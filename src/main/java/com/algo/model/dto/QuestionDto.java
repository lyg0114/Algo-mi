package com.algo.model.dto;

import com.algo.model.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.thymeleaf.util.StringUtils;

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
  private String questionType;
  private String registDt;

  public Question converTnEntity(ModelMapper modelMapper) {
    Question.QuestionBuilder builder = Question.builder();
    if (!StringUtils.isEmpty(this.title)) { builder.title(this.title); }
    if (!StringUtils.isEmpty(this.url)) { builder.url(this.url); }
    if (!StringUtils.isEmpty(this.fromSource)) { builder.fromSource(this.fromSource); }
    if (!StringUtils.isEmpty(this.questionType)) { builder.questionType(this.questionType); }
    if (this.reviewCount > 0) { builder.reviewCount(this.reviewCount); }
    return builder.build();
  }
}
