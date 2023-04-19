package com.project.fri.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * packageName    : com.project.fri.exception fileName       : ControllerAdvisor date           :
 * 2023-04-19 description    :
 */
@RestControllerAdvice
@Builder
public class ControllerAdvisor {
  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ExceptionResponse NotFoundExceptionHandler(Exception e, ServerHttpRequest request){
    return ExceptionResponse.createExceptionResponse(e, HttpStatus.BAD_REQUEST, request);
  }
}
