package com.securityfirst.winterctf.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckDuplicateUserIdResponse {
  private final boolean result;
}
