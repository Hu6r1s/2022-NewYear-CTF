package com.securityfirst.winterctf.controller.notice;

import com.securityfirst.winterctf.dto.response.notice.NoticeListResponse;
import com.securityfirst.winterctf.dto.response.notice.NoticeResponse;
import com.securityfirst.winterctf.entity.Notice;
import com.securityfirst.winterctf.mapper.NoticeListMapper;
import com.securityfirst.winterctf.service.NoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

  @Autowired
  NoticeService noticeService;

  @GetMapping
  public NoticeListResponse getNoticeList() {
    List<NoticeListMapper> noticeList = noticeService.getNoticeList();
    return new NoticeListResponse(true, noticeList);
  }

  @GetMapping("/{id}")
  public NoticeResponse getNotice(@PathVariable("id") String id) {
    Optional<Notice> notice = noticeService.getNotice(Long.parseLong(id));
    if (notice.isPresent()) {
      return new NoticeResponse(true, notice.get());
    }
    return new NoticeResponse(false, null);
  }
}
