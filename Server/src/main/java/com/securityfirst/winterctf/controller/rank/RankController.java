package com.securityfirst.winterctf.controller.rank;

import com.securityfirst.winterctf.dto.response.challenge.ChallengeTop10ListResponse;
import com.securityfirst.winterctf.dto.response.rank.SolverRankResponse;
import com.securityfirst.winterctf.mapper.ChallengeListMapper;
import com.securityfirst.winterctf.mapper.SolverRankMapper;
import com.securityfirst.winterctf.repository.ChallengeRepository;
import com.securityfirst.winterctf.service.ChallengeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rank")
public class RankController {

  @Autowired
  ChallengeRepository challengeRepository;
  @Autowired
  ChallengeService challengeService;

  @GetMapping("/solver/{type}")
  public SolverRankResponse getSolverRank(@PathVariable("type") String type) {
    switch (type) {
      case "all": {
        List<SolverRankMapper> solverRank = challengeRepository.getSolverRankingAll();
        return new SolverRankResponse(true, solverRank);
      }
      case "yb": {
        List<SolverRankMapper> solverRank = challengeRepository.getSolverRankingYB();
        return new SolverRankResponse(true, solverRank);
      }
      case "ob": {
        List<SolverRankMapper> solverRank = challengeRepository.getSolverRankingOB();
        return new SolverRankResponse(true, solverRank);
      }
      case "nb": {
        List<SolverRankMapper> solverRank = challengeRepository.getSolverRankingNB();
        return new SolverRankResponse(true, solverRank);
      }
      default:
        return new SolverRankResponse(false, null);
    }
  }

  @GetMapping("/challenge")
  public ChallengeTop10ListResponse getChallengeRank() {
    List<ChallengeListMapper> challengeList = challengeService.getTop10Challenge();
    return new ChallengeTop10ListResponse(true, challengeList);
  }
}
