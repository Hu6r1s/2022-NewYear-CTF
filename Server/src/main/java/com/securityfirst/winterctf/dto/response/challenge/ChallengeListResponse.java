package com.securityfirst.winterctf.dto.response.challenge;

import com.securityfirst.winterctf.mapper.ChallengeListMapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChallengeListResponse {
  private boolean success;
  private List<ChallengeListMapper> challengeList;
}


