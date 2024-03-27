package com.algo.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignUpResponse {
  private String email;
  private String message;
}
