package com.securityfirst.winterctf.controller.myinfo;

import com.securityfirst.winterctf.dto.response.myinfo.MySolveList;
import com.securityfirst.winterctf.dto.response.myinfo.MyInfoResponse;
import com.securityfirst.winterctf.mapper.UserInfoMapper;
import com.securityfirst.winterctf.repository.UserInfoRepository;
import com.securityfirst.winterctf.security.UserInfoPrincipal;
import com.securityfirst.winterctf.service.MyInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@Validated
@RequestMapping("/api/info")
public class MyInfoController {

  @Autowired
  MyInfoService myInfoService;

  @Autowired
  UserInfoRepository userInfoRepository;

  @GetMapping
  public MyInfoResponse getMyInfo(@AuthenticationPrincipal UserInfoPrincipal userInfoPrincipal) {
    int myScore = 0;

    List<MySolveList> mySolveList = myInfoService.getMySolveList(userInfoPrincipal.getUserInfo().getUserId());

    for (MySolveList challenge : mySolveList) {
      myScore += challenge.getChallengeScore();
    }

    return new MyInfoResponse(true, userInfoPrincipal.getUserInfo().getNick(), myScore, mySolveList);
  }

  @GetMapping("/{nick}")
  public MyInfoResponse getInfo(@PathVariable("nick") @NotBlank String nick) {
    int myScore = 0;

    if (myInfoService.validateNick(nick)) {
      List<MySolveList> mySolveList = myInfoService.getUserSolveList(nick);
      UserInfoMapper userInfo = userInfoRepository.getUserInfoByNick(nick);
      for (MySolveList challenge : mySolveList) {
        myScore += challenge.getChallengeScore();
      }
      return new MyInfoResponse(true, userInfo.getNick(), myScore, mySolveList);
    }

    else {
      return new MyInfoResponse(false, null, 0, null);
    }
  }

}
