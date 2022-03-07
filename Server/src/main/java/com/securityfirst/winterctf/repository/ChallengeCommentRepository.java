package com.securityfirst.winterctf.repository;

import com.securityfirst.winterctf.entity.ChallengeComment;
import com.securityfirst.winterctf.mapper.ChallengeCommentListMapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.LockModeType;


@Repository
public interface ChallengeCommentRepository extends JpaRepository<ChallengeComment ,Long> {
  List<ChallengeCommentListMapper> findByChallengeId(Long challengeId);

  boolean existsByChallengeIdAndUserId(Long challengeId, String userId);
  void deleteByChallengeId(Long challengeId);
}
