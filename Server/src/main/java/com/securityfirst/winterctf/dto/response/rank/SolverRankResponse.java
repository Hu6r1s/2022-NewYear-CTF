package com.securityfirst.winterctf.dto.response.rank;

import com.securityfirst.winterctf.mapper.SolverRankMapper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class SolverRankResponse {

  private boolean success;
  private List<SolverRankMapper> solverRank;

}
