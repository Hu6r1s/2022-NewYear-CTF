package com.securityfirst.winterctf.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomBadRequestException {
  private boolean error;
  private String errorInfo;
}
