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
@Table(name = "challenge_flag_history")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengeFlagHistory {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "challenge_id")
  private Long challengeId;

  @Column(name = "flag")
  private String flag;

  @Column(name = "nick")
  private String nick;

  @Column(name = "ip")
  private String ip;

  @Column(name = "success")
  private boolean success;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

}
