package com.securityfirst.winterctf.repository;

import com.securityfirst.winterctf.entity.UserInfo;
import com.securityfirst.winterctf.mapper.UserInfoMapper;
import com.securityfirst.winterctf.mapper.UserListMapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

  Optional<UserInfo> findByUserId(String userId);

  boolean existsByUserId(String userId);

  boolean existsByNickAndIsAdmin(String nick, boolean isAdmin);

  @Query(value = "select nick as nick, id as id from user_info", nativeQuery = true)
  List<UserListMapper> getUserListByAdmin();

  UserInfoMapper getUserInfoById(Long userId);

  UserInfoMapper getUserInfoByUserId(String userId);

  UserInfoMapper getUserInfoByNick(String nick);

  @Query(value = "select * from user_info where id =:id", nativeQuery = true)
  UserInfo getUserInfoByModify(@Param("id") Long id);

}
