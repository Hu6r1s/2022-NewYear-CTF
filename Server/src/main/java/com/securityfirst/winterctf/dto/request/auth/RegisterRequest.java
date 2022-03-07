package com.securityfirst.winterctf.dto.request.auth;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

/*
회원가입 요청 DTO
*/

public class RegisterRequest {

  @NotBlank
  @Pattern(regexp = "^[a-z0-9_]{4,20}$")
  private String userId;

  @NotBlank
  @Pattern(regexp = "^[\\w\\W]{8,20}$")
  private String password;

  @NotBlank
  @Pattern(regexp = "^[\\w\\Wㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$")
  private String nick;

  @NotBlank
  private String team;

  @AssertTrue
  public boolean isTeamValidation() {
    if (team == null || team.trim().isEmpty()) {
      return false;
    }
    return (team.equals("SecurityFirst_NB") || team.equals("SecurityFirst_YB") || team.equals("SecurityFirst_OB"));
  }

}
