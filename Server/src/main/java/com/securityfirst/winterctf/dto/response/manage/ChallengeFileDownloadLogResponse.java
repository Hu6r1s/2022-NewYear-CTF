package com.securityfirst.winterctf.dto.response.manage;

import com.securityfirst.winterctf.entity.ChallengeFileDownloadLog;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChallengeFileDownloadLogResponse {
  private boolean success;
  private List<ChallengeFileDownloadLog> logList;
}
