package com.securityfirst.winterctf.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notice")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Notice {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "notice_title")
  private String noticeTitle;

  @Column(name = "notice_content")
  private String noticeContent;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public void modifyNoticeTitle(String noticeTitle) {
    this.noticeTitle = noticeTitle;
  }

  public void modifyNoticeContent(String noticeContent) {
    this.noticeContent = noticeContent;
  }
}
