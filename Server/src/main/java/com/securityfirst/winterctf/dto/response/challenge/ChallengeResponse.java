package com.securityfirst.winterctf.dto.response.challenge;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChallengeResponse {
  private boolean success;
  private boolean solved;
  private String challengeName;
  private String challengeDescription;
  private int challengeScore;
  private int challengeStage;
  private String challengeAuthor;
  private int challengeViews;
  private int challengeSolverCount;
  private String challengeCategory;
  private boolean existChallengeFile;
}
