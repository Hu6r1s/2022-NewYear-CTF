package com.securityfirst.winterctf.repository;

import com.securityfirst.winterctf.entity.RequestLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

}
