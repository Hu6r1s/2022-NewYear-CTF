package com.securityfirst.winterctf.mapper;

import java.time.LocalDateTime;

public interface ChallengeCommentListMapper {
  String getNick();
  LocalDateTime getCreatedAt();
  String getComment();
  Long getId();
}
