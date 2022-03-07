package com.securityfirst.winterctf.dto.response.manage;

import com.securityfirst.winterctf.mapper.ChallengeListByAdminMapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChallengeListByAdminResponse {

  private boolean success;
  private List<ChallengeListByAdminMapper> challengeList;

}
