package com.securityfirst.winterctf.entity;

import java.time.LocalDateTime;

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

@Entity
@Table(name = "challenge_view_log")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengeViewLog {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "challenge_id")
  private Long challengeId;

  @Column(name = "user_info_id")
  private Long userInfoId;

  @Column(name = "nick")
  private String nick;

  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
