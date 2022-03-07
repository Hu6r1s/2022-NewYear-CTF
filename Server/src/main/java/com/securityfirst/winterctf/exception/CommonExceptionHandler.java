package com.securityfirst.winterctf.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<CustomBadRequestException> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
    return new ResponseEntity<>(new CustomBadRequestException(false, "Wrong Input"), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {UsernameNotFoundException.class})
  protected ResponseEntity<CustomBadRequestException> noUser(ConstraintViolationException e, WebRequest request) {
    return new ResponseEntity<>(new CustomBadRequestException(true, "Wrong Input"), HttpStatus.UNAUTHORIZED);
  }

}
