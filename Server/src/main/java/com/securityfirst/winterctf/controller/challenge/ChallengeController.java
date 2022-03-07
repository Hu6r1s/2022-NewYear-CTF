package com.securityfirst.winterctf.controller.challenge;

import com.securityfirst.winterctf.dto.request.challenge.ChallengeSolveRequest;
import com.securityfirst.winterctf.dto.response.challenge.ChallengeCommentListResponse;
import com.securityfirst.winterctf.dto.response.challenge.ChallengeListByCategoryResponse;
import com.securityfirst.winterctf.dto.response.challenge.ChallengeListResponse;
import com.securityfirst.winterctf.dto.response.challenge.ChallengeResponse;
import com.securityfirst.winterctf.dto.response.challenge.ChallengeSolveResponse;
import com.securityfirst.winterctf.dto.response.challenge.ChallengeTop10ListResponse;
import com.securityfirst.winterctf.entity.Challenge;
import com.securityfirst.winterctf.mapper.ChallengeCommentListMapper;
import com.securityfirst.winterctf.mapper.ChallengeListMapper;
import com.securityfirst.winterctf.security.UserInfoPrincipal;
import com.securityfirst.winterctf.service.ChallengeService;
import com.securityfirst.winterctf.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/challenge")
@Validated
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class ChallengeController {

  @Autowired
  ChallengeService challengeService;
  @Autowired
  LogService logService;

  @GetMapping
  public ChallengeListResponse getChallengeList() {
    List<ChallengeListMapper> challengeList = challengeService.getChallengeList();
    return new ChallengeListResponse(true, challengeList);
  }

  @GetMapping("/{challengeId}")
  public ChallengeResponse getChallengeInfo(
      @PathVariable("challengeId") @NotBlank @Pattern(regexp = "^[0-9]*$") String challengeId,
      @AuthenticationPrincipal UserInfoPrincipal userInfoPrincipal) {
    Challenge challenge = challengeService.getChallenge(Long.parseLong(challengeId));

    boolean solved = challengeService
        .checkAlreadySolve(Long.parseLong(challengeId), userInfoPrincipal.getUserInfo().getUserId());

    if (challenge != null) {
      challengeService.plusChallengeViewCount(Long.parseLong(challengeId), userInfoPrincipal.getUserInfo().getId(),
          userInfoPrincipal.getUserInfo().getNick());
      return ChallengeResponse.builder()
          .success(true)
          .solved(solved)
          .existChallengeFile(challenge.getChallengeFileName() != null && challenge.getChallengeFileUrl() != null)
          .challengeAuthor(challenge.getChallengeAuthor())
          .challengeName(challenge.getChallengeName())
          .challengeScore(challenge.getChallengeScore())
          .challengeStage(challenge.getChallengeStage())
          .challengeCategory(challenge.getChallengeCategory())
          .challengeViews(challenge.getChallengeViews())
          .challengeSolverCount(challenge.getChallengeSolverCount())
          .challengeDescription(challenge.getChallengeDescription())
          .build();
    } else {
      return ChallengeResponse.builder()
          .success(false)
          .build();
    }
  }

  @GetMapping("/category/{category}")
  public ChallengeListByCategoryResponse getChallengeListByCategory(
      @PathVariable("category") @NotBlank String category) {
    List<ChallengeListMapper> challengeList = challengeService.getChallengeByCategory(category);
    return new ChallengeListByCategoryResponse(true, challengeList);
  }

  @GetMapping("/comment/{challengeId}")
  public ChallengeCommentListResponse getChallengeComment(
      @PathVariable("challengeId") @NotBlank @Pattern(regexp = "^[0-9]*$") String challengeId) {
    List<ChallengeCommentListMapper> challengeCommentList = challengeService
        .getChallengeComment(Long.parseLong(challengeId));
    return new ChallengeCommentListResponse(true, challengeCommentList);
  }

  @PostMapping("/{challengeId}")
  public synchronized ChallengeSolveResponse solveChallenge(
      @PathVariable("challengeId") @NotBlank @Pattern(regexp = "^[0-9]*$") String challengeId,
      @AuthenticationPrincipal UserInfoPrincipal userInfoPrincipal,
      @RequestBody ChallengeSolveRequest challengeSolveRequest,
      HttpServletRequest request) {
    boolean solveResult = challengeService
        .solveChallenge(Long.parseLong(challengeId), challengeSolveRequest.getFlag(), userInfoPrincipal);

    String ip = request.getHeader("X-FORWARDED-FOR");
    if (ip == null) ip = request.getRemoteAddr();

    logService.addFlagHistory(Long.parseLong(challengeId), challengeSolveRequest.getFlag(),
        userInfoPrincipal.getUserInfo().getNick(), ip, solveResult);

    return new ChallengeSolveResponse(solveResult);
  }


  @GetMapping("/file/{id}")
  public ResponseEntity<Resource> getChallengeFile(
      @PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id,
      @AuthenticationPrincipal UserInfoPrincipal userInfoPrincipal, HttpServletRequest request)
      throws IOException {

    Challenge challenge = challengeService.validateRequestChallengeFile(Long.parseLong(id));

    if (challenge == null) {
      return ResponseEntity.status(403).body(null);
    }

    String ip = request.getHeader("X-FORWARDED-FOR");
    if (ip == null) ip = request.getRemoteAddr();

    logService.addChallengeFileDownLoadHistory(Long.parseLong(id), userInfoPrincipal.getUserInfo().getNick(), ip);

    Path path = Paths.get(challenge.getChallengeFileUrl());
    Resource resource = new InputStreamResource(Files.newInputStream(path));
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + challenge.getChallengeFileName() + "\"")
        .body(resource);
  }


}
