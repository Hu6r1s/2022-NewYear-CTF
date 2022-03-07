package com.securityfirst.winterctf.controller.auth;

import com.securityfirst.winterctf.dto.request.auth.LoginRequest;
import com.securityfirst.winterctf.dto.request.auth.RegisterRequest;
import com.securityfirst.winterctf.dto.response.auth.CheckDuplicateUserIdResponse;
import com.securityfirst.winterctf.dto.response.auth.LoginResponse;
import com.securityfirst.winterctf.dto.response.auth.RegisterResponse;
import com.securityfirst.winterctf.security.JwtAuthProvider;
import com.securityfirst.winterctf.security.UserInfoPrincipal;
import com.securityfirst.winterctf.service.AuthService;
import com.securityfirst.winterctf.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@Validated
public class AuthController {

  @Autowired
  AuthService authService;

  @Autowired
  JwtAuthProvider jwtAuthProvider;

  @Autowired
  LogService logService;

  @PostMapping("/api/register")
  @ResponseBody
  public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
    boolean registerResult = authService.register(registerRequest);

    if (registerResult) {
      return new RegisterResponse(true);
    } else {
      return new RegisterResponse(false);
    }

  }

  @GetMapping("/api/register/id/{userId}")
  public CheckDuplicateUserIdResponse checkDuplicateUserIdResponse(
      @PathVariable @NotBlank @Pattern(regexp = "^[a-z0-9_]{4,20}$") String userId) {
    return new CheckDuplicateUserIdResponse(authService.checkUserIdDuplicate(userId));
  }

  @PostMapping("/api/login")
  @ResponseBody
  public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
    boolean loginResult = authService.login(loginRequest);

    if (!loginResult) {
      return new LoginResponse(false, "");
    }

    String accessToken = jwtAuthProvider.createAccessToken(loginRequest.getUserId());
//    String refreshToken = jwtAuthProvider.createRefreshToken(loginRequest.getUserId());

    String ip = request.getHeader("X-FORWARDED-FOR");
    // System.out.println("{}", ip);
    if (ip == null) ip = request.getRemoteAddr();

    logService.addLoginHistory(loginRequest.getUserId(), ip);
    return new LoginResponse(true, accessToken);

  }

//  @GetMapping("/test")
//  @ResponseBody
//  public UserInfoPrincipal test(@AuthenticationPrincipal UserInfoPrincipal userInfoPrincipal) {
//    return userInfoPrincipal;
//  }


  /*
  @GetMapping("/register/nick/{nick}")
  public CheckDuplicateNickResponse checkDuplicateNickResponse(@PathVariable @NotBlank @Pattern(regexp = "^[\\w\\Wㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$") String nick) {
    return new CheckDuplicateNickResponse(authService.checkNickDuplicate(nick));
  }
  */

}
