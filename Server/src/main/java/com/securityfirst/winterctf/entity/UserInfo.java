package com.securityfirst.winterctf.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserInfo implements UserDetails {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", unique = true, length = 25, nullable = false)
  private String userId;

  @Column(name = "password", length = 200, nullable = false)
  private String password;

  @Column(name = "nick", length = 45, nullable = false)
  private String nick;

  @Column(name = "team", length = 45, nullable = false)
  private String team;

  @Column(name = "last_solve_time")
  private LocalDateTime lastSolveTime;

  @Column(name = "is_ban")
  private boolean isBan;

  @Column(name = "is_admin")
  private boolean isAdmin;

  public boolean getIsBan() {
    return this.isBan;
  }
  // UserDetails

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

    if (this.isAdmin) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 관리자 권한 부여
    }

    return authorities;

  }

  @Override
  public String getUsername() {
    return this.userId;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return !this.isBan;
  }


  public void modifyUserId(String userId) {
    this.userId = userId;
  }

  public void modifyNick(String nick) {
    this.nick = nick;
  }

  public void modifyPassword(String password) {
    this.password = password;
  }

  public void modifyIsBan(boolean isBan) {
    this.isBan = isBan;
  }

  public void modifyIsAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public void modifyTeam(String team) {
    this.team = team;
  }

  public void modifyLastSolveTime(LocalDateTime lastSolveTime) {
    this.lastSolveTime = lastSolveTime;
  }
}
