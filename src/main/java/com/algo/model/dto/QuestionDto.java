package com.algo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model.dto
 * @since : 20.11.23
 */
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDto {

  private Long id;
  private String title;
  private String url;
  private String fromSource;
  @Builder.Default
  private Integer reviewCount = 0;
}
