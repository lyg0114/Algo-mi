package com.algo.exception;

import com.algo.exception.custom.SignUpFailException;
import com.algo.exception.dto.ExceptionResponse;
import java.util.NoSuchElementException;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(value = {NoSuchElementException.class, SignUpFailException.class, IllegalArgumentException.class})
  public ResponseEntity<ExceptionResponse> handleCustomException(RuntimeException ex) {
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
