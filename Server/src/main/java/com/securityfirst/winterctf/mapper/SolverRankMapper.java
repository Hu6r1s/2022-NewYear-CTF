package com.securityfirst.winterctf.mapper;

import java.time.LocalDateTime;

public interface SolverRankMapper {
  int getScoreSum();
  String getNick();
  String getTeam();
  LocalDateTime getLastSolveTime();
}
