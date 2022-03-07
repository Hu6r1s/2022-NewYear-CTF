package com.securityfirst.winterctf.dto.response.challenge;

import com.securityfirst.winterctf.mapper.ChallengeCommentListMapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ChallengeCommentListResponse {
  private boolean success;
  private List<ChallengeCommentListMapper> challengeCommentList;
}
