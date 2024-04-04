package com.algo.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author : iyeong-gyo
 * @package : com.algo.exception.dto
 * @since : 04.04.24
 */
@Builder
@Data
@AllArgsConstructor
public class ExceptionResponse {
  private int status;
  private String message;
}
