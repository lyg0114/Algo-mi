package com.algo.exception;

import com.algo.exception.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(value = {IllegalArgumentException.class})
  public ResponseEntity<ExceptionResponse> handleCustomException(IllegalArgumentException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ExceptionResponse
            .builder()
            .message(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST.value())
            .build()
        );
  }
}
