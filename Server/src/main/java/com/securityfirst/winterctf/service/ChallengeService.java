package com.securityfirst.winterctf.service;

import com.securityfirst.winterctf.dto.request.manage.AddChallenge;
import com.securityfirst.winterctf.entity.Challenge;
import com.securityfirst.winterctf.entity.ChallengeComment;
import com.securityfirst.winterctf.entity.ChallengeViewLog;
import com.securityfirst.winterctf.entity.UserInfo;
import com.securityfirst.winterctf.mapper.ChallengeCommentListMapper;
import com.securityfirst.winterctf.mapper.ChallengeListByAdminMapper;
import com.securityfirst.winterctf.mapper.ChallengeListMapper;
import com.securityfirst.winterctf.repository.ChallengeCommentRepository;
import com.securityfirst.winterctf.repository.ChallengeRepository;
import com.securityfirst.winterctf.repository.ChallengeViewLogRepository;
import com.securityfirst.winterctf.repository.UserInfoRepository;
import com.securityfirst.winterctf.security.UserInfoPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import java.util.regex.Pattern;

@Service
public class ChallengeService {

  @Autowired
  ChallengeRepository challengeRepository;
  @Autowired
  ChallengeCommentRepository challengeCommentRepository;
  @Autowired
  ChallengeViewLogRepository challengeViewLogRepository;
  @Autowired
  UserInfoRepository userInfoRepository;

  public final static boolean OpenChallenge = true;

  public List<ChallengeListMapper> getChallengeList() {
    return challengeRepository.findByChallengeOpenOrderByOpenedAtDesc(true);
  }

  public Challenge getChallenge(Long id) {
    return challengeRepository.findByIdAndChallengeOpen(id, OpenChallenge);
  }

  public List<ChallengeListMapper> getChallengeByCategory(String category) {
    return challengeRepository.findByChallengeCategoryAndChallengeOpenOrderByChallengeScoreAsc(category, OpenChallenge);
  }

  public List<ChallengeListByAdminMapper> getChallengeListByAdmin(String category) {
    return challengeRepository.getChallengeListByAdmin(category);
  }

  public Challenge getChallengeByAdmin(Long id) throws Exception {
    return challengeRepository.findById(id).orElseThrow(() -> new Exception("no"));
  }

  public List<ChallengeCommentListMapper> getChallengeComment(Long challengeId) {
    return challengeCommentRepository.findByChallengeId(challengeId);
  }

  @Transactional
  public boolean solveChallenge(Long challengeId, String flag, UserInfoPrincipal userInfoPrincipal) {

    // 전달받은 문제번호가 존재하는 문제인지 확인
    if (!validateChallengeId(challengeId)) {
      return false;
    }

    //이미 풀었는지 확인
    if (checkAlreadySolve(challengeId, userInfoPrincipal.getUserInfo().getUserId())) {
      return false;
    }

    // 플래그가 맞는지 확인
    if (!validateFlag(challengeId, flag)) {
      return false;
    }

    // 관리자는 풀이 처리 안함
    if (userInfoPrincipal.getUserInfo().isAdmin()) {
      return true;
    }

    // 문제를 풀었음을 기록
    addComment(challengeId, userInfoPrincipal);

    // 문제 풀이자 수 + 1
    plusSolverCount(challengeId);

    if (challengeRepository.getChallengeByModify(challengeId).getChallengeCategory().equals("event")) { // event 문제는 다이나믹 X
      return true;
    }

    // 다이나믹 스코어링
    dynamicScoring(challengeId);

    // 최근 풀이시간 업데이트
    UserInfo userInfo = userInfoRepository.getUserInfoByModify(userInfoPrincipal.getUserInfo().getId());
    userInfo.modifyLastSolveTime(LocalDateTime.now());
    userInfoRepository.save(userInfo);

    return true;
  }

  @Transactional
  public boolean checkAlreadySolve(Long challengeId, String userId) {
    return challengeCommentRepository.existsByChallengeIdAndUserId(challengeId, userId);
  }

  @Transactional
  public boolean validateChallengeId(Long challengeId) {
    return challengeRepository.existsByIdAndChallengeOpen(challengeId, OpenChallenge);
  }

  @Transactional
  public boolean validateFlag(Long challengeId, String flag) { 
    String answer = challengeRepository.findByIdAndChallengeOpen(challengeId, OpenChallenge).getChallengeFlag().trim();
    // return answer.equals(flag);
  
  /*
  [Flag 중복 체크를 위한 검증 코드 추가]
  사용자 플래그 양식 : SF{TSET_TEST_<hash>}
  운영진 : 'SF{TEST_TEST}' 형태의 플래그를 DB에 추가
  로직 : DB에 운영진이 추가한 플래그에서 맨뒤 '}'를 제거한 값인 'SF{TEST_TEST'가 사용자가 입력한 플래그내의 앞부분과 같은지 확인
    - 존재하면 true 리턴
    - 존재하지 않으면 false 리턴
  
  기존과의 변경점:
    - 기존 : equals를 사용하여 무조건 같아야만 true 리턴
    - 변경 : 앞부분이 같으면 true 리턴
      - 따라서 운영자는 대회가 끝난뒤 뒤의 해시값이 같은 플래그가 있다면 치팅을 하였다고 확실하게 알 수 있음.
      - 단, 리버싱, 포렌식의 경우에는 항상 플래그에 다른 해시를 추가할 수 없기 불가능
  */
    String check_answer = answer.substring(0, answer.length()-1);    // 맨 뒤 '}' 제거 
    String check_flag = flag.substring(0, check_answer.length()); // 사용자가 입력한 플래그의 앞부분 슬라이싱
    return check_flag.equals(check_answer);
  }

  @Transactional
  public void addComment(Long challengeId, UserInfoPrincipal userInfoPrincipal) {
    ChallengeComment challengeComment = ChallengeComment.builder()
        .comment(getCommentString(challengeId))
        .challengeId(challengeId)
        .userId(userInfoPrincipal.getUserInfo().getUserId())
        .nick(userInfoPrincipal.getUserInfo().getNick())
        .createdAt(LocalDateTime.now())
        .build();
    challengeCommentRepository.save(challengeComment);
  }

  @Transactional
  public void plusSolverCount(Long challengeId) {
    Challenge challenge = challengeRepository.findByIdAndChallengeOpenForUpdate(challengeId, OpenChallenge);
    challenge.plusSolverCount();
    challengeRepository.save(challenge);
  }

  @Transactional
  public List<ChallengeListMapper> getTop10Challenge() {
    return challengeRepository.findTop10ByChallengeOpenOrderByChallengeScoreAsc(OpenChallenge);
  }

  public boolean modifyChallengeName(Long id, String challengeName) {
    Challenge challenge = challengeRepository.getChallengeByModify(id);
    challenge.modifyChallengeName(challengeName);
    challengeRepository.save(challenge);
    return true;
  }

  public boolean modifyChallengeCategory(Long id, String challengeCategory) {
    Challenge challenge = challengeRepository.getChallengeByModify(id);
    challenge.modifyChallengeCategory(challengeCategory);
    challengeRepository.save(challenge);
    return true;
  }

  public boolean modifyChallengeDescription(Long id, String challengeDescription) {
    Challenge challenge = challengeRepository.getChallengeByModify(id);
    challenge.modifyChallengeDescription(challengeDescription);
    challengeRepository.save(challenge);
    return true;
  }

  public boolean modifyChallengeFlag(Long id, String challengeFlag) {
    Challenge challenge = challengeRepository.getChallengeByModify(id);
    challenge.modifyChallengeFlag(challengeFlag);
    challengeRepository.save(challenge);
    return true;
  }

  public boolean modifyChallengeOpen(Long id, boolean challengeOpen) {
    Challenge challenge = challengeRepository.getChallengeByModify(id);
    challenge.modifyChallengeOpen(challengeOpen);
    challenge.modifyChallengeOpenAt(LocalDateTime.now());
    challengeRepository.save(challenge);
    return true;
  }

  public Long addChallenge(AddChallenge addChallenge, UserInfoPrincipal userInfoPrincipal) {

    Challenge challenge = Challenge.builder()
        .challengeAuthor(userInfoPrincipal.getUserInfo().getNick())
        .challengeCategory(addChallenge.getChallengeCategory())
        .challengeScore(addChallenge.getChallengeScore())
        .challengeStage(addChallenge.getChallengeStage())
        .challengeSolverCount(0)
        .challengeViews(0)
        .challengeDescription(addChallenge.getChallengeDescription())
        .challengeFlag(addChallenge.getChallengeFlag())
        .challengeName(addChallenge.getChallengeName())
        .challengeOpen(false)
        .build();
    int stage = challenge.getChallengeStage();
    int score;
    switch (stage) {
      case 1: 
        score = 50;
        break;
      case 2:
        score = 100;
        break;
      case 3: 
        score = 150;
        break;
      case 4: 
        score = 200;
        break;
      case 5: 
        score = 300;
        break;
      default: 
        score = 0;
        break;
    }

    challenge.dynamicScoring(score);

    challengeRepository.save(challenge);
    return challengeRepository.save(challenge).getId();
  }

  public boolean addChallengeFile(Long id, MultipartFile multipartFile) throws IOException {
    multipartFile.transferTo(new File("/server/file/" + multipartFile.getOriginalFilename()));
    Challenge challenge = challengeRepository.getChallengeByModify(id);
    challenge.modifyChallengeFileName(multipartFile.getOriginalFilename());
    challenge.modifyChallengeFileUrl("/server/file/" + multipartFile.getOriginalFilename());
    challengeRepository.save(challenge);
    return true;
  }

  public Challenge validateRequestChallengeFile(Long id) {
    if (!challengeRepository.existsByIdAndChallengeOpen(id, true)) {
      return null;
    }

    Challenge challenge = challengeRepository.findByIdAndChallengeOpen(id, true);

    if (challenge.getChallengeFileName() == null || challenge.getChallengeFileUrl() == null) {
      return null;
    }

    return challenge;
  }

  @Transactional
  public boolean deleteChallenge(Long id) {
    challengeRepository.deleteById(id);
    challengeCommentRepository.deleteByChallengeId(id);
    return true;
  }

  public void plusChallengeViewCount(Long challengeId, Long userId, String nick) { // 조회 수 증가 로직
    if (existsByChallengeIdAndUserInfoId(challengeId, userId)) {
      return;
    }

    addChallengeViewLog(challengeId, userId, nick);
    Challenge challenge = challengeRepository.getChallengeByModify(challengeId);
    challenge.plusChallengeViewCount();
    challengeRepository.save(challenge);
  }

  public boolean existsByChallengeIdAndUserInfoId(Long challengeId, Long userInfoId) { // 조회 수 중복 검사 로직
    return challengeViewLogRepository.existsByChallengeIdAndUserInfoId(challengeId, userInfoId);
  }

  public void addChallengeViewLog(Long challengeId, Long userId, String nick) {
    ChallengeViewLog challengeViewLog = ChallengeViewLog.builder()
        .challengeId(challengeId)
        .userInfoId(userId)
        .nick(nick)
        .createdAt(LocalDateTime.now())
        .build();
    challengeViewLogRepository.save(challengeViewLog);
  }

  @Transactional
  public void dynamicScoring(Long challengeId) {
    Challenge challenge = challengeRepository.getChallengeByModify(challengeId);
    /* int minimum = 100;
    int minSolverCount = 40;
    int initial = 1000;
    int solveCount = challenge.getChallengeSolverCount();
    if (solveCount > minSolverCount) {
      solveCount = minSolverCount;
    } */
    int stage = challenge.getChallengeStage();
    int score;
    switch (stage) {
      case 1: 
        score = 50;
        break;
      case 2:
        score = 100;
        break;
      case 3: 
        score = 150;
        break;
      case 4: 
        score = 200;
        break;
      case 5: 
        score = 300;
        break;
      default: 
        score = 0;
        break;
    }
    // int score = (int) (((minimum - initial) / (Math.pow(minSolverCount, 2))) * Math.pow(solveCount, 2)) + initial;

    challenge.dynamicScoring(score);

    challengeRepository.save(challenge);
  }



  public String getCommentString(Long challengeId) {

    String[] comment = {
        "별점 10점 만점 중에 10점 드리겠습니다.",
        "최고의 게임~~ 내인생 다섯손가락 안에든다. (도전과제 99% 달성완료)!",
        "하는 방법 알고나서는 재밌어용",
        "게임 인생은 이 게임을 하기 전과 후로 나뉩니다!",
        "띵작.......",
        "안해보시면 인생 절반 손해보시는거에요 !",
        "노력하지않고 무언가를 잘 해내는사람을 천재라한다면 난 천재가 아니다.",
        "정말 재밌어요 >_< 하면 할수록 더 느는 것 같아요ㅎㅎ",
        "CTF 입문용으로 아주 좋은 작품",
        "Give me DLC PLZㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ",
        "내 인생 게임은 이거다ㅋ"
    };

    Random random = new Random();
    int num = random.nextInt(14);

    if (num <= 10) return comment[num];
    Challenge challenge = challengeRepository.getChallengeByModify(challengeId);

    switch (num){
      case 11:
        return "역시 믿고하는 " + challenge.getChallengeAuthor() + "의 게임이네요..";
      case 12:
        return challenge.getChallengeAuthor() + "의 게임.. 매력있음. 자꾸만 꺼내먹고 싶다..!!!";
      case 13:
        return challenge.getChallengeAuthor() + "의 게임은 정말 좋네요. 안 좋은 게임이 없다..";
      case 14:
        return challenge.getChallengeAuthor() + "의 다음 게임 벌써부터 기다려진다..";
      default:
        return null;
    }

  }

}
