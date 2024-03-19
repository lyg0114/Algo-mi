package com.algo.question.dto;

import java.time.LocalDateTime;
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
public class QuestionResponse {

  private Long id;
  private String title;
  private String url;
  private String fromSource;
  @Builder.Default
  private Integer reviewCount = 0;
  private String questionType;
  private String content;
  private String registDt;
  private LocalDateTime fromDt;
  private LocalDateTime toDt;
}
