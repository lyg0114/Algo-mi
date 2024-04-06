package com.algo.question.domain;

import com.algo.auth.domain.UserInfo;
import com.algo.common.domain.BaseEntity;
import com.algo.question.dto.QuestionResponse;
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
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.style.ToStringCreator;
import org.thymeleaf.util.StringUtils;

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

  @NotBlank
  @Column(name = "question_type")
  private String questionType;

  @NotBlank
  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @NotNull
  @Builder.Default
  @Column(name = "review_count")
  private Integer reviewCount = 0;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserInfo userInfo;

  private Long getUserId() {
    if (userInfo == null) {
      return null;
    }
    return this.userInfo.getUserId();
  }

  public QuestionResponse converToDto(ModelMapper modelMapper) {
    QuestionResponse result = modelMapper.map(this, QuestionResponse.class);
    result.setId(this.questionId);
    result.setContent(this.userInfo.getEmail());
    result.setRegistDt(this.getCreatedDt().format(DateTimeFormatter
        .ofPattern("yyyy-MM-dd")));
    return result;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public void update(Question willUpdateQuestion) {
    if (!StringUtils.isEmpty(willUpdateQuestion.getTitle())) {
      this.title = willUpdateQuestion.getTitle();
    }
    if (!StringUtils.isEmpty(willUpdateQuestion.getUrl())) {
      this.url = willUpdateQuestion.getUrl();
    }
    if (!StringUtils.isEmpty(willUpdateQuestion.getFromSource())) {
      this.fromSource = willUpdateQuestion.getFromSource();
    }
    if (willUpdateQuestion.getReviewCount() > 0) {
      this.reviewCount = willUpdateQuestion.getReviewCount();
    }
    if (!StringUtils.isEmpty(willUpdateQuestion.getContent())) {
      this.content = willUpdateQuestion.getContent();
    }
  }

  @Override
  public String toString() {
    return new ToStringCreator(this)
        .append("id", this.getQuestionId())
        .append("title", this.getTitle())
        .append("url", this.getUrl())
        .append("fromSource", this.getFromSource())
        .append("reviewCount", this.getReviewCount())
        .append("content", this.getContent())
        .append("user_id", getUserId())
        .toString();
  }
}
