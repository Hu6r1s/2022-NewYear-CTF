package com.securityfirst.winterctf.dto.response.manage;

import com.securityfirst.winterctf.entity.Challenge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChallengeByAdminResponse {
  private boolean success;
  private Challenge challenge;
}
