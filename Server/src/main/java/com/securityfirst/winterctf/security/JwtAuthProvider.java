package com.securityfirst.winterctf.security;

import com.securityfirst.winterctf.entity.UserInfo;
import com.securityfirst.winterctf.repository.UserInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Service
public class JwtAuthProvider {

  private final Key secretKey;

  public JwtAuthProvider(@Value("${jwtSecretKey}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
  }

  @Autowired
  private CustomUserInfoDetailService customUserInfoDetailService;

  @Autowired
  private UserInfoRepository userInfoRepository;

  long accessTokenValidTime = 36000L * 60 * 60;
  long refreshTokenValidTime = 2000L * 60 * 60;

  public String createAccessToken(String userId) {

    Claims claims = Jwts.claims().setSubject(userId);
    Date currentTime = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(currentTime)
        .setExpiration(new Date(currentTime.getTime() + accessTokenValidTime))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String createRefreshToken(String userId) {

    Claims claims = Jwts.claims().setSubject(userId);
    Date currentTime = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(currentTime)
        .setExpiration(new Date(currentTime.getTime() + refreshTokenValidTime))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }
  /*
  토큰에 담겨있는 UID를 파싱한 후 해당 UID를 통해 데이터베이스에서 유저 정보를 불러와 인증 객체에 담는다.
  */

  public Authentication getAuthentication(String token) {

    String userId = getUserIdFromToken(token);
    UserInfo userInfo = customUserInfoDetailService.loadUserByUsername(userId);

    UserInfoPrincipal userInfoPrincipal = new UserInfoPrincipal(userInfo);
    return new UsernamePasswordAuthenticationToken(userInfoPrincipal, token, userInfo.getAuthorities());

  }

  /*
  Http Header에서 인증 토큰을 파싱한다.
  */

  public String getTokenFromHeader(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  public String getUserIdFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validate(String token) {
    try {
      if (token == null) {
        return false;
      }
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
      return true;
    } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public boolean checkBan(String userId) {
    return customUserInfoDetailService.loadUserByUsername(userId).getIsBan();
  }
}
