package com.securityfirst.winterctf.security;

import com.securityfirst.winterctf.entity.RequestLog;
import com.securityfirst.winterctf.mapper.UserInfoMapper;
import com.securityfirst.winterctf.repository.RequestLogRepository;
import com.securityfirst.winterctf.repository.UserInfoRepository;
import com.securityfirst.winterctf.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
Http 요청이 오면 JWT 토큰을 검증하는 이 Filter를 먼저 거쳐간다.
*/
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtAuthProvider jwtAuthProvider;
  private final LogService logService;
  public JwtAuthenticationFilter(JwtAuthProvider jwtAuthProvider, LogService logService) {
    this.jwtAuthProvider = jwtAuthProvider;
    this.logService = logService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String ip = request.getHeader("X-FORWARDED-FOR");
    if (ip == null) ip = request.getRemoteAddr();

    String token = jwtAuthProvider.getTokenFromHeader(request);

    /*
    헤더에 있는 토큰이 유효한 토큰인지 검증하고 유효한 토큰이라면 인증 Context안에 저장한다.
    */

    if (jwtAuthProvider.validate(token)) { // 토큰이 유효한지 검사
      String userId = jwtAuthProvider.getUserIdFromToken(token);
      if (!jwtAuthProvider.checkBan(userId)) { // 정지된 유저인지 검사
        logService.addRequestLog(userId, request.getRequestURI(), ip);
        Authentication authentication = jwtAuthProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
        return;
      }
    }
    logService.addRequestLog(null, request.getRequestURI(), ip);
    filterChain.doFilter(request, response);

  }

}
