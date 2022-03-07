package com.securityfirst.winterctf.repository;

import com.securityfirst.winterctf.entity.Challenge;
import com.securityfirst.winterctf.entity.UserInfo;
import com.securityfirst.winterctf.mapper.ChallengeListByAdminMapper;
import com.securityfirst.winterctf.mapper.ChallengeListMapper;
import com.securityfirst.winterctf.mapper.SolverRankMapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.LockModeType;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

  List<ChallengeListMapper> findByChallengeOpenOrderByOpenedAtDesc(boolean challengeOpen);

  Challenge findByIdAndChallengeOpen(Long id, boolean challengeOpen);

  boolean existsByIdAndChallengeOpen(Long id, boolean challengeOpen);

  List<ChallengeListMapper> findTop10ByChallengeOpenOrderByChallengeScoreAsc(boolean challengeOpen);

  List<ChallengeListMapper> findByChallengeCategoryAndChallengeOpenOrderByChallengeScoreAsc(String challengeCategory,
      boolean challengeOpen);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select h from Challenge as h where h.id = :id AND h.challengeOpen = :challengeOpen")
  Challenge findByIdAndChallengeOpenForUpdate(@Param("id") Long id, @Param("challengeOpen") boolean challengeOpen);

  @Query("select a from Challenge as a join ChallengeComment as b on a.id = b.challengeId where b.userId = :userId order by b.createdAt desc")
  List<Challenge> getMySolveList(@Param("userId") String userId);

  @Query("select a from Challenge as a join ChallengeComment as b on a.id = b.challengeId where b.nick = :nick order by b.createdAt desc")
  List<Challenge> getUserSolveList(@Param("nick") String nick);

  @Query(value = "select SUM(challenge.challenge_score) as scoreSum , challenge_comment.nick as nick, user_info.last_solve_time as lastSolveTime, user_info.team as Team  from challenge_comment join challenge on challenge_comment.challenge_id = challenge.id AND challenge.challenge_category != 'event' join user_info on challenge_comment.nick = user_info.nick group by challenge_comment.nick, user_info.last_solve_time, user_info.team order by scoreSum desc, user_info.last_solve_time", nativeQuery = true)
  List<SolverRankMapper> getSolverRankingAll();

  @Query(value = "select SUM(challenge.challenge_score) as scoreSum , challenge_comment.nick as nick, user_info.last_solve_time as lastSolveTime, user_info.team as Team from challenge_comment join challenge on challenge_comment.challenge_id = challenge.id AND challenge.challenge_category != 'event' join user_info on challenge_comment.nick = user_info.nick where user_info.team = 'SecurityFirst_YB'  group by challenge_comment.nick, user_info.last_solve_time, user_info.team order by scoreSum desc , user_info.last_solve_time", nativeQuery = true)
  List<SolverRankMapper> getSolverRankingYB();

  @Query(value = "select SUM(challenge.challenge_score) as scoreSum , challenge_comment.nick as nick, user_info.last_solve_time as lastSolveTime, user_info.team as Team from challenge_comment join challenge on challenge_comment.challenge_id = challenge.id AND challenge.challenge_category != 'event' join user_info on challenge_comment.nick = user_info.nick where user_info.team = 'SecurityFirst_OB' group by challenge_comment.nick, user_info.last_solve_time, user_info.team order by scoreSum desc , user_info.last_solve_time", nativeQuery = true)
  List<SolverRankMapper> getSolverRankingOB();

  @Query(value = "select SUM(challenge.challenge_score) as scoreSum , challenge_comment.nick as nick, user_info.last_solve_time as lastSolveTime, user_info.team as Team from challenge_comment join challenge on challenge_comment.challenge_id = challenge.id AND challenge.challenge_category != 'event' join user_info on challenge_comment.nick = user_info.nick where user_info.team = 'SecurityFirst_NB' group by challenge_comment.nick, user_info.last_solve_time, user_info.team order by scoreSum desc , user_info.last_solve_time", nativeQuery = true)
  List<SolverRankMapper> getSolverRankingNB();

  @Query(value = "select challenge_name as challengeName, id as id from challenge where challenge_category = :category", nativeQuery = true)
  List<ChallengeListByAdminMapper> getChallengeListByAdmin(@Param("category") String category);

  @Query(value = "select * from challenge where id =:id", nativeQuery = true)
  Challenge getChallengeByModify(@Param("id") Long id);
}
