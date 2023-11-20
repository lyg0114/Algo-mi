package com.algo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.style.ToStringCreator;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model
 * @since : 18.11.23
 */
@Getter
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

  @NotBlank
  @Column(name = "review_count")
  private Integer reviewCount;

  @Override
  public String toString() {
    return new ToStringCreator(this).append("id", this.getQuestionId())
        .append("title", this.getTitle())
        .append("url", this.getUrl())
        .append("fromSource", this.getFromSource())
        .append("reviewCount", this.getReviewCount())
        .toString();
  }
}
