package com.algo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model
 * @since : 18.11.23
 */
@Entity
@Table(name = "question")
public class Question extends BaseEntity {

  @Column(name = "title")
  @NotBlank
  private String title;

  @Column(name = "from_source")
  @NotBlank
  private String fromSource;

  @Column(name = "review_count")
  @NotBlank
  private Integer reviewCount;
}
