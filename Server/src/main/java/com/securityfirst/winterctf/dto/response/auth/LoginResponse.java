package com.securityfirst.winterctf.dto.response.auth;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
  private final boolean success;
  private final String accessToken;
//  private final String refreshToken;
}
