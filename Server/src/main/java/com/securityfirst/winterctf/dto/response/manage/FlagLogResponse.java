package com.securityfirst.winterctf.dto.response.manage;

import com.securityfirst.winterctf.entity.ChallengeFlagHistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FlagLogResponse {
  private boolean success;
  private List<ChallengeFlagHistory> logList;
}
