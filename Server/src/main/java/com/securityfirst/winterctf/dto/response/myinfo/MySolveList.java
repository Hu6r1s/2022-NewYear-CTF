package com.securityfirst.winterctf.dto.response.myinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MySolveList {
  private Long id;
  private String challengeName;
  private int challengeScore;
  private String challengeAuthor;
  private String challengeCategory;
}
