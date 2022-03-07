package com.securityfirst.winterctf.repository;

import com.securityfirst.winterctf.entity.ChallengeFileDownloadLog;
import com.securityfirst.winterctf.entity.ChallengeFlagHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeFileDownloadRepository extends JpaRepository<ChallengeFileDownloadLog, Long> {
  @Query(value = "SELECT * from challenge_file_download_log order by id desc LIMIT 10 OFFSET :page", nativeQuery = true)
  List<ChallengeFileDownloadLog> getChallengeFileDownloadLogByPaging(@Param("page") int page);

  @Query(value = "SELECT COUNT(id) as count from challenge_file_download_log where nick =:nick", nativeQuery = true)
  int getChallengeFileDownloadLogCountByNick(@Param("nick") String nick);

  @Query(value = "SELECT COUNT(id) as count from challenge_file_download_log where challenge_id =:challenge_id", nativeQuery = true)
  int getChallengeFileDownloadLogCountByChallengeId(@Param("challenge_id") Long challenge_id);

  @Query(value = "SELECT * from challenge_file_download_log where nick =:nick order by id desc LIMIT 10 OFFSET :page", nativeQuery = true)
  List<ChallengeFileDownloadLog> getChallengeFileDownloadLogByPagingAndNick(@Param("page") int page, @Param("nick") String nick);

  @Query(value = "SELECT * from challenge_file_download_log where challenge_id =:challengeId order by id desc LIMIT 10 OFFSET :page", nativeQuery = true)
  List<ChallengeFileDownloadLog> getChallengeFileDownloadLogByPagingAndChallengeId(@Param("page") int page, @Param("challengeId") Long challengeId);
}
