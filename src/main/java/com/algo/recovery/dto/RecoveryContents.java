package com.algo.recovery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author : iyeong-gyo
 * @package : com.algo.recovery.dto
 * @since : 06.04.24
 */
@Builder
@AllArgsConstructor
@Data
public class RecoveryContents {

  private String email;
  private String contents;
}
