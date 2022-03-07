package com.securityfirst.winterctf.mapper;

public interface UserInfoMapper {
  String getUserId();
  String getNick();
  String getTeam();
  boolean getIsBan();
  boolean getIsAdmin();
}
