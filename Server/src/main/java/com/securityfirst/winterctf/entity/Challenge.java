package com.securityfirst.winterctf.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "challenge")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Challenge {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "challenge_name")
  private String challengeName;

  @Column(name = "challenge_score")
  private int challengeScore;

  @Column(name = "challenge_stage")
  private int challengeStage;

  @Column(name = "challenge_author")
  private String challengeAuthor;

  @Column(name = "challenge_description")
  private String challengeDescription;

  @Column(name = "challenge_solver_count")
  private int challengeSolverCount;

  @Column(name = "challenge_views")
  private int challengeViews;

  @Column(name = "challenge_file_url")
  private String challengeFileUrl;

  @Column(name = "challenge_file_name")
  private String challengeFileName;

  @Column(name = "challenge_flag")
  private String challengeFlag;

  @Column(name = "challenge_open")
  private boolean challengeOpen;

  @Column(name = "challenge_category")
  private String challengeCategory;

  @Column(name = "opened_at")
  private LocalDateTime openedAt;

  public void plusSolverCount() {
    this.challengeSolverCount += 1;
  }

  public void plusChallengeViewCount() {
    this.challengeViews += 1;
  }

  public void dynamicScoring(int challengeScore) {
    this.challengeScore = challengeScore;
  }

  public void modifyStage(int challengeStage) {
    this.challengeStage = challengeStage;
  }

  public void modifyChallengeName(String challengeName) {
    this.challengeName = challengeName;
  }

  public void modifyChallengeCategory(String challengeCategory) {
    this.challengeCategory = challengeCategory;
  }

  public void modifyChallengeDescription(String challengeDescription) {
    this.challengeDescription = challengeDescription;
  }

  public void modifyChallengeFlag(String challengeFlag) {
    this.challengeFlag = challengeFlag;
  }

  public void modifyChallengeOpen(boolean challengeOpen) {
    this.challengeOpen = challengeOpen;
  }

  public void modifyChallengeOpenAt(LocalDateTime openedAt) {
    this.openedAt = openedAt;
  }

  public void modifyChallengeFileName(String challengeFileName) {
    this.challengeFileName = challengeFileName;
  }

  public void modifyChallengeFileUrl(String challengeFileUrl) {
    this.challengeFileUrl = challengeFileUrl;
  }
}
