package com.securityfirst.winterctf.service;

import com.securityfirst.winterctf.entity.ChallengeFileDownloadLog;
import com.securityfirst.winterctf.entity.ChallengeFlagHistory;
import com.securityfirst.winterctf.entity.LoginLog;
import com.securityfirst.winterctf.entity.RequestLog;
import com.securityfirst.winterctf.mapper.UserInfoMapper;
import com.securityfirst.winterctf.repository.ChallengeFileDownloadRepository;
import com.securityfirst.winterctf.repository.ChallengeFlagHistoryRepository;
import com.securityfirst.winterctf.repository.LoginLogRepository;
import com.securityfirst.winterctf.repository.RequestLogRepository;
import com.securityfirst.winterctf.repository.UserInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

  @Autowired
  ChallengeFlagHistoryRepository challengeFlagHistoryRepository;

  @Autowired
  LoginLogRepository loginLogRepository;

  @Autowired
  UserInfoRepository userInfoRepository;

  @Autowired
  ChallengeFileDownloadRepository challengeFileDownloadRepository;

  @Autowired
  RequestLogRepository requestLogRepository;

  public void addFlagHistory(Long challengeId, String flag, String nick, String ip, boolean success) {
    ChallengeFlagHistory challengeFlagHistory = ChallengeFlagHistory.builder()
        .challengeId(challengeId)
        .flag(flag)
        .nick(nick)
        .ip(ip)
        .success(success)
        .createdAt(LocalDateTime.now())
        .build();
    challengeFlagHistoryRepository.save(challengeFlagHistory);
  }

  public void addLoginHistory(String userId, String ip) {
    String nick = userInfoRepository.getUserInfoByUserId(userId).getNick();

    LoginLog loginLog = LoginLog.builder().nick(nick).ip(ip).createdAt(LocalDateTime.now()).build();
    loginLogRepository.save(loginLog);
  }

  public void addChallengeFileDownLoadHistory(Long challengeId, String nick, String ip) {
    ChallengeFileDownloadLog challengeFileDownloadLog = ChallengeFileDownloadLog.builder()
        .challengeId(challengeId)
        .nick(nick)
        .ip(ip)
        .createdAt(LocalDateTime.now())
        .build();

    challengeFileDownloadRepository.save(challengeFileDownloadLog);
  }

  public void addRequestLog(String userId, String url, String ip) {
    if (userId != null) {
      UserInfoMapper userInfo = userInfoRepository.getUserInfoByUserId(userId);
      requestLogRepository.save(
          RequestLog.builder()
              .nick(userInfo.getNick())
              .url(url)
              .createdAt(LocalDateTime.now())
              .ip(ip)
              .build());
    } else {
      requestLogRepository.save(
          RequestLog.builder()
              .nick("로그인 없이 요청")
              .url(url)
              .createdAt(LocalDateTime.now())
              .ip(ip)
              .build());
    }

  }

  public int getLoginLogAllCount() {
    return (int) loginLogRepository.count();
  }

  public int getFlagLogAllCount() {
    return (int) challengeFlagHistoryRepository.count();
  }

  public int getChallengeFileDownloadLogAllCount() {
    return (int) challengeFileDownloadRepository.count();
  }

  public List<LoginLog> getLoginLogAll(int page) {
    return loginLogRepository.getLoginLogByPaging(page);
  }

  public List<ChallengeFlagHistory> getFlagLogAll(int page) {
    return challengeFlagHistoryRepository.getFlagLogByPaging(page);
  }

  public List<ChallengeFileDownloadLog> getChallengeFileDownloadLogAll(int page) {
    return challengeFileDownloadRepository.getChallengeFileDownloadLogByPaging(page);
  }

  public int getLoginLogCountByNick(String nick) {
    return loginLogRepository.getLoginLogCountByNick(nick);
  }

  public int getLoginLogCountByIp(String ip) {
    return loginLogRepository.getLoginLogCountByIp(ip);
  }

  public int getFlagLogCountByNick(String nick) {
    return challengeFlagHistoryRepository.getFlagLogCountByNick(nick);
  }

  public int getFlagLogCountByChallengeId(Long challengeId) {
    return challengeFlagHistoryRepository.getFLagLogCountByChallengeId(challengeId);
  }

  public int getChallengeFileDownloadLogCountByNick(String nick) {
    return challengeFileDownloadRepository.getChallengeFileDownloadLogCountByNick(nick);
  }

  public int getChallengeFileDownloadByChallengeId(Long challengeId) {
    return challengeFileDownloadRepository.getChallengeFileDownloadLogCountByChallengeId(challengeId);
  }

  public List<LoginLog> getLoginLogByNick(String nick, int page) {
    return loginLogRepository.getLoginLogByPagingAndNick(page, nick);
  }

  public List<LoginLog> getLoginLogByIp(String ip, int page) {
    return loginLogRepository.getLoginLogByPagingAndIp(page, ip);
  }

  public List<ChallengeFlagHistory> getFlagLogByNick(String nick, int page) {
    return challengeFlagHistoryRepository.getFlagLogByPagingAndNick(page, nick);
  }

  public List<ChallengeFlagHistory> getFlagLogByChallengeId(Long challengeId, int page) {
    return challengeFlagHistoryRepository.getFlagLogByPagingAndChallengeId(page, challengeId);
  }

  public List<ChallengeFileDownloadLog> getChallengeFileDownloadByNick(String nick, int page) {
    return challengeFileDownloadRepository.getChallengeFileDownloadLogByPagingAndNick(page, nick);
  }

  public List<ChallengeFileDownloadLog> getChallengeFileDownloadByChallengeId(Long challengeId, int page) {
    return challengeFileDownloadRepository.getChallengeFileDownloadLogByPagingAndChallengeId(page, challengeId);
  }
}
