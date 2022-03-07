package com.securityfirst.winterctf.security;

import com.securityfirst.winterctf.entity.UserInfo;

import org.springframework.security.core.userdetails.User;

import java.io.Serializable;

import lombok.Getter;

/*
기본적으로 Spring Security에서 저장 되는 인증 객체의 Principal은 Id, Password, Role 이다
따라서 Principal의 자료형인 User를 상속받고 그 안에 추가적인 정보를 담을수 있게 클래스를 새로 만든다.
 */
@Getter
public class UserInfoPrincipal extends User implements Serializable {

  private final UserInfo userInfo;

  // User 클래스를 상속받기 때문에 super를 통해 User의 생성자를 수행한 후
  // 추가적인 정보가 담겨있는 UserInfo를 인자로 받아 데이터를 담는다.
  public UserInfoPrincipal(UserInfo userInfo) {
    super(userInfo.getUserId(), userInfo.getPassword(), userInfo.getAuthorities());
    this.userInfo = userInfo;
  }

}
