package com.securityfirst.winterctf.dto.request.manage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddChallenge {
  private String challengeName;
  private String challengeCategory;
  private String challengeDescription;
  private String challengeFlag;
  private int challengeScore;
  private int challengeStage;
}
