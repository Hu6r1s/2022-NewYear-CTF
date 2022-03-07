package com.securityfirst.winterctf.repository;

import com.securityfirst.winterctf.entity.LoginLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {

  @Query(value = "SELECT * from login_log order by id desc LIMIT 10 OFFSET :page", nativeQuery = true)
  List<LoginLog> getLoginLogByPaging(@Param("page") int page);

  @Query(value = "SELECT * from login_log where nick =:nick order by id desc LIMIT 10 OFFSET :page", nativeQuery = true)
  List<LoginLog> getLoginLogByPagingAndNick(@Param("page") int page, @Param("nick") String nick);

  @Query(value = "SELECT * from login_log where ip =:ip order by id desc LIMIT 10 OFFSET :page", nativeQuery = true)
  List<LoginLog> getLoginLogByPagingAndIp(@Param("page") int page, @Param("ip") String ip);

  @Query(value = "SELECT COUNT(id) as count from login_log where nick =:nick", nativeQuery = true)
  int getLoginLogCountByNick(@Param("nick") String nick);

  @Query(value = "SELECT COUNT(id) as count from login_log where ip =:ip", nativeQuery = true)
  int getLoginLogCountByIp(@Param("ip") String ip);
}
