package com.securityfirst.winterctf.dto.request.manage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyNotice {
  private String noticeTitle;
  private String noticeContent;
}
