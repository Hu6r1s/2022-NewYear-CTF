package com.securityfirst.winterctf.dto.response.manage;

import com.securityfirst.winterctf.entity.LoginLog;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class LoginLogResponse {
  private boolean success;
  private List<LoginLog> logList;
}
