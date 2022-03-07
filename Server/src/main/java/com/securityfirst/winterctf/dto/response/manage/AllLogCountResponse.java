package com.securityfirst.winterctf.dto.response.manage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AllLogCountResponse {
  private boolean success;
  private int count;
}
