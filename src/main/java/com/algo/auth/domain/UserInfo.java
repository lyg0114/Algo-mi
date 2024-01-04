package com.algo.auth.domain;

import com.algo.common.domain.BaseEntity;
import com.algo.question.domain.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : iyeong-gyo
 * @package : com.algo.model
 * @since : 20.11.23
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @NotBlank
  @Column(name = "email", updatable = false, nullable = false)
  private String email;

  @NotBlank
  @Column(name = "user_name")
  private String userName;

  @JsonIgnore
  @Column(name = "passwd", nullable = false)
  private String passwd;

  @Default
  @OneToMany(mappedBy = "userInfo")
  private List<Question> questions = new ArrayList<>();

  @Column(name = "role")
  private String role;

  @Override
  public String toString() {
    return "UserInfo{" +
        "userId=" + userId +
        ", email='" + email + '\'' +
        ", userName='" + userName + '\'' +
        ", passwd='" + passwd + '\'' +
        ", questions=" + questions +
        ", role=" + role +
        '}';
  }
}
