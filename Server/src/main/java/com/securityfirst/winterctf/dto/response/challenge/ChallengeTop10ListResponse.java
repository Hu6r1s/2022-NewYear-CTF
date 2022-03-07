package com.securityfirst.winterctf.dto.response.challenge;

import com.securityfirst.winterctf.mapper.ChallengeListMapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChallengeTop10ListResponse {
  private boolean success;
  private List<ChallengeListMapper> challengeList;
}
