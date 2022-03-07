package com.securityfirst.winterctf.dto.request.challenge;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChallengeSolveRequest {

  @NotBlank
  private String flag;

}
