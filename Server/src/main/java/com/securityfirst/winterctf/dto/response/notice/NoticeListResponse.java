package com.securityfirst.winterctf.dto.response.notice;

import com.securityfirst.winterctf.mapper.NoticeListMapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NoticeListResponse {
  private boolean success;
  private List<NoticeListMapper> noticeList;
}
