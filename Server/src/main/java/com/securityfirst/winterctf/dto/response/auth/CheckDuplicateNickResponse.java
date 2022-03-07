package com.securityfirst.winterctf.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CheckDuplicateNickResponse {
  private final boolean result;
}
