package com.securityfirst.winterctf.dto.response.manage;

import com.securityfirst.winterctf.mapper.UserListMapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserListResponse {
  private boolean success;
  private List<UserListMapper> userList;
}
