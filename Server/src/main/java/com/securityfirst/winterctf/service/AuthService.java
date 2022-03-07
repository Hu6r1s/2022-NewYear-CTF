package com.securityfirst.winterctf.service;

import com.securityfirst.winterctf.dto.request.auth.LoginRequest;
import com.securityfirst.winterctf.dto.request.auth.RegisterRequest;
import com.securityfirst.winterctf.entity.UserInfo;
import com.securityfirst.winterctf.mapper.UserInfoMapper;
import com.securityfirst.winterctf.mapper.UserListMapper;
import com.securityfirst.winterctf.repository.UserInfoRepository;
import com.securityfirst.winterctf.security.JwtAuthProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

  @Autowired
  UserInfoRepository userInfoRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtAuthProvider jwtAuthProvider;

  public boolean register(RegisterRequest registerRequest) {

    /*
    아이디, 닉네임 중복 검사 로직
     */

    if (checkUserIdDuplicate(registerRequest.getUserId())) {
      return false;
    }

    String password = passwordEncoder.encode(registerRequest.getPassword());

    UserInfo userInfo = UserInfo.builder()
        .userId(registerRequest.getUserId())
        .password(password)
        .nick(registerRequest.getNick())
        .team(registerRequest.getTeam())
        .build();

    userInfoRepository.save(userInfo);

    return true;

  }

  public boolean checkUserIdDuplicate(String userId) {
    return userInfoRepository.existsByUserId(userId);
  }

  public boolean login(LoginRequest loginRequest) {
    UserInfo userInfo = userInfoRepository.findByUserId(loginRequest.getUserId()).orElse(null);

    // ID가 없는 경우
    if (userInfo == null) {
      return false;
    }
    //Ban 유저인 경우
    if (userInfo.getIsBan()) {
      System.out.println(userInfo.getIsBan());
      return false;
    }

    // 비밀번호가 틀린 경우
    return passwordEncoder.matches(loginRequest.getPassword(), userInfo.getPassword());
  }

  /*
  public boolean checkNickDuplicate(String nick) {
    return userInfoRepository.existsByNick(nick);
  }
  */

  public List<UserListMapper> getUserListByAdmin() {
    return userInfoRepository.getUserListByAdmin();
  }

  public UserInfoMapper getUserInfoById(Long userId) {
    return userInfoRepository.getUserInfoById(userId);
  }

  public boolean modifyUserId(Long id, String userId) {
    UserInfo userInfo = userInfoRepository.getUserInfoByModify(id);
    userInfo.modifyUserId(userId);
    userInfoRepository.save(userInfo);
    return true;
  }

  public boolean modifyNick(Long id, String nick) {
    UserInfo userInfo = userInfoRepository.getUserInfoByModify(id);
    userInfo.modifyNick(nick);
    userInfoRepository.save(userInfo);
    return true;
  }

  public boolean modifyPassword(Long id, String password) {
    UserInfo userInfo = userInfoRepository.getUserInfoByModify(id);
    userInfo.modifyPassword(passwordEncoder.encode(password));
    userInfoRepository.save(userInfo);
    return true;
  }

  public boolean modifyIsBan(Long id, boolean isBan) {
    UserInfo userInfo = userInfoRepository.getUserInfoByModify(id);
    userInfo.modifyIsBan(isBan);
    userInfoRepository.save(userInfo);
    return true;
  }

  public boolean modifyIsAdmin(Long id, boolean isAdmin) {
    UserInfo userInfo = userInfoRepository.getUserInfoByModify(id);
    userInfo.modifyIsAdmin(isAdmin);
    userInfoRepository.save(userInfo);
    return true;
  }

  public boolean modifyTeam(Long id, String team) {
    UserInfo userInfo = userInfoRepository.getUserInfoByModify(id);
    userInfo.modifyTeam(team);
    userInfoRepository.save(userInfo);
    return true;
  }

}
