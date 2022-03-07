package com.securityfirst.winterctf.mapper;

/*
데이터베이스 Select 후 원하는 데이터만 가져오도록 Mapper 생성
*/
public interface ChallengeListMapper {
  Long getId();
  String getChallengeName();
  int getChallengeScore();
  int getChallengeStage();
  String getChallengeAuthor();
  String getChallengeCategory();
}
