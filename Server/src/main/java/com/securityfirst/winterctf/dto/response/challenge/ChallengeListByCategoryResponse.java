package com.securityfirst.winterctf.dto.response.challenge;

import com.securityfirst.winterctf.mapper.ChallengeListMapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ChallengeListByCategoryResponse {
  private boolean success;
  private List<ChallengeListMapper> challengeList;
}
