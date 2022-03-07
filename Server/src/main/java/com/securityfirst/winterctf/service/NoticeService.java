package com.securityfirst.winterctf.service;

import com.securityfirst.winterctf.dto.request.manage.AddNotice;
import com.securityfirst.winterctf.dto.request.manage.ModifyNotice;
import com.securityfirst.winterctf.entity.Notice;
import com.securityfirst.winterctf.mapper.NoticeListMapper;
import com.securityfirst.winterctf.repository.NoticeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

  @Autowired
  NoticeRepository noticeRepository;

  public void addNotice(AddNotice addNotice) {
    Notice notice = Notice.builder()
        .noticeTitle(addNotice.getNoticeTitle())
        .noticeContent(addNotice.getNoticeContent())
        .createdAt(LocalDateTime.now()).build();
    noticeRepository.save(notice);
  }

  public Optional<Notice> getNotice(Long id) {
    return noticeRepository.findById(id);
  }

  public List<NoticeListMapper> getNoticeList() {
    return noticeRepository.getNoticeList();
  }

  public void modifyNotice(Long id, ModifyNotice modifyNotice) {
    Notice notice = noticeRepository.getNoticeByModify(id);
    notice.modifyNoticeTitle(modifyNotice.getNoticeTitle());
    notice.modifyNoticeContent(modifyNotice.getNoticeContent());
    noticeRepository.save(notice);
  }

  public void deleteNotice(Long id) {
    noticeRepository.deleteById(id);
  }

}
