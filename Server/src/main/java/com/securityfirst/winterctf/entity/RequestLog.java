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
@Table(name = "request_log")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLog {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "url")
  private String url;

  @Column(name = "ip")
  private String ip;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "nick")
  private String nick;

}
