package com.algo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model
 * @since : 18.11.23
 */
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
  @Column(name = "from_source")
  private String fromSource;

  @NotBlank
  @Column(name = "review_count")
  private Integer reviewCount;
}
