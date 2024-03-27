package com.algo.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ErrorRequest {

  HttpStatus httpStatus;
  String message;
}
