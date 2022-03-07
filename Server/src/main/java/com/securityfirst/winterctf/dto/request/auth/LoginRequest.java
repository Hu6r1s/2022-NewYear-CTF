package com.securityfirst.winterctf.dto.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  private String userId;
  private String password;
}
