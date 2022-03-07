package com.securityfirst.winterctf.dto.response.notice;

import com.securityfirst.winterctf.entity.Notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NoticeResponse {
  private boolean success;
  private Notice notice;
}
