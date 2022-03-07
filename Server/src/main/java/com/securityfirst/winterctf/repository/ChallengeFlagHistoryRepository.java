package com.securityfirst.winterctf.repository;

import com.securityfirst.winterctf.entity.ChallengeFlagHistory;
import com.securityfirst.winterctf.entity.LoginLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeFlagHistoryRepository extends JpaRepository<ChallengeFlagHistory, Long> {
  @Query(value = "SELECT * from challenge_flag_history order by id desc LIMIT 10 OFFSET :page", nativeQuery = true)
  List<ChallengeFlagHistory> getFlagLogByPaging(@Param("page") int page);

  @Query(value = "SELECT COUNT(id) as count from challenge_flag_history where nick =:nick", nativeQuery = true)
  int getFlagLogCountByNick(@Param("nick") String nick);

  @Query(value = "SELECT COUNT(id) as count from challenge_flag_history where challenge_id =:challenge_id", nativeQuery = true)
  int getFLagLogCountByChallengeId(@Param("challenge_id") Long challenge_id);

  @Query(value = "SELECT * from challenge_flag_history where nick =:nick order by id desc LIMIT 10 OFFSET :page", nativeQuery = true)
  List<ChallengeFlagHistory> getFlagLogByPagingAndNick(@Param("page") int page, @Param("nick") String nick);

  @Query(value = "SELECT * from challenge_flag_history where challenge_id =:challengeId order by id desc LIMIT 10 OFFSET :page", nativeQuery = true)
  List<ChallengeFlagHistory> getFlagLogByPagingAndChallengeId(@Param("page") int page, @Param("challengeId") Long challengeId);
}
