package com.securityfirst.winterctf.repository;

import com.securityfirst.winterctf.entity.ChallengeViewLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeViewLogRepository extends JpaRepository <ChallengeViewLog, Long> {
  boolean existsByChallengeIdAndUserInfoId(Long challengeId, Long userInfoId);
}
