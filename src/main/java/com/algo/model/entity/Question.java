package com.algo.model.entity;

import com.algo.model.dto.QuestionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.style.ToStringCreator;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model
 * @since : 18.11.23
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private Long questionId;

  @NotBlank
  @Column(name = "title")
  private String title;

  @NotBlank
  @Column(name = "url")
  private String url;

  @NotBlank
  @Column(name = "from_source")
  private String fromSource;

  @NotNull
  @Column(name = "review_count")
  private Integer reviewCount;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserInfo userInfo;

  @Override
  public String toString() {
    return new ToStringCreator(this).append("id", this.getQuestionId())
        .append("title", this.getTitle())
        .append("url", this.getUrl())
        .append("fromSource", this.getFromSource())
        .append("reviewCount", this.getReviewCount())
        .append("user_id", this.userInfo.getUserId())
        .toString();
  }

  public QuestionDto converToDto(ModelMapper modelMapper) {
    QuestionDto map = modelMapper.map(this, QuestionDto.class);
    map.setId(this.questionId);
    return map;
  }
}
