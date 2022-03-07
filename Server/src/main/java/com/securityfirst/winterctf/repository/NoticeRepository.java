package com.securityfirst.winterctf.repository;

import com.securityfirst.winterctf.entity.Challenge;
import com.securityfirst.winterctf.entity.Notice;
import com.securityfirst.winterctf.mapper.NoticeListMapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

  @Query(value = "select notice_title as noticeTitle, id as id from notice", nativeQuery = true)
  List<NoticeListMapper> getNoticeList();

  @Query(value = "select * from notice where id =:id", nativeQuery = true)
  Notice getNoticeByModify(@Param("id") Long id);
}
