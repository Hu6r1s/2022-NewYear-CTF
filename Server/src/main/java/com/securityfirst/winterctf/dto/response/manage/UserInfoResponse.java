package com.securityfirst.winterctf.dto.response.manage;

import com.securityfirst.winterctf.mapper.UserInfoMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
  private boolean success;
  private UserInfoMapper userInfoMapper;
}
