package com.securityfirst.winterctf.dto.response.myinfo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MyInfoResponse {
  private boolean success;
  private String nick;
  private int myScore;
  private List<MySolveList> mySolveList;
}
