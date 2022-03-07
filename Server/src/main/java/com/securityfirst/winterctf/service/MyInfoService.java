package com.securityfirst.winterctf.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.securityfirst.winterctf.dto.response.myinfo.MySolveList;
import com.securityfirst.winterctf.entity.Challenge;
import com.securityfirst.winterctf.entity.ChallengeComment;
import com.securityfirst.winterctf.entity.QChallenge;
import com.securityfirst.winterctf.entity.QChallengeComment;
import com.securityfirst.winterctf.mapper.ChallengeListMapper;
import com.securityfirst.winterctf.repository.ChallengeCommentRepository;
import com.securityfirst.winterctf.repository.ChallengeRepository;
import com.securityfirst.winterctf.repository.UserInfoRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
public class MyInfoService {

  @Autowired
  ChallengeCommentRepository challengeCommentRepository;

  @Autowired
  ChallengeRepository challengeRepository;

  @Autowired
  UserInfoRepository userInfoRepository;

  public List<MySolveList> getMySolveList(String userId) {
    ModelMapper modelMapper = new ModelMapper();
    List<Challenge> list = challengeRepository.getMySolveList(userId);
    return list.stream().map(item -> modelMapper.map(item,MySolveList.class)).collect(
        Collectors.toList());
  }

  public List<MySolveList> getUserSolveList(String nick) {
    ModelMapper modelMapper = new ModelMapper();
    List<Challenge> list = challengeRepository.getUserSolveList(nick);
    return list.stream().map(item -> modelMapper.map(item,MySolveList.class)).collect(
        Collectors.toList());
  }

  public boolean validateNick(String nick) {
    return userInfoRepository.existsByNickAndIsAdmin(nick, false);
  }

}
