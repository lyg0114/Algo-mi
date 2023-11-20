package com.algo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model
 * @since : 20.11.23
 */
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @NotBlank
  @Column(updatable = false, nullable = false)
  private String email;

  @NotBlank
  @Column(name = "user_name")
  private String userName;

  @JsonIgnore
  @Column(nullable = false)
  private String passwd;

  @OneToMany
  @JoinColumn(name = "question_id")
  private List<Question> questions = new ArrayList<>();
}
