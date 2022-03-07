package com.securityfirst.winterctf.security;

import com.securityfirst.winterctf.entity.UserInfo;
import com.securityfirst.winterctf.repository.UserInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.security.SecurityException;

@Service
public class CustomUserInfoDetailService implements UserDetailsService {

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Override
  public UserInfo loadUserByUsername(String userId)
      throws UsernameNotFoundException {
    Optional<UserInfo> userInfo = userInfoRepository.findByUserId(userId);

    if (!userInfo.isPresent()) {
      throw new UsernameNotFoundException((userId));
    }

    return userInfo.get();
  }
}
