package com.algo.question.dto;

import java.time.LocalDateTime;

import org.thymeleaf.util.StringUtils;

import com.algo.common.dto.UserInfoRequest;
import com.algo.question.domain.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
public class QuestionRequest extends UserInfoRequest {

	private Long id;
	private String title;
	private String url;
	private String fromSource;
	private String searchTerm;
	private String content;
	@Builder.Default
	private Integer reviewCount = 0;
	private String questionType;
	private String registDt;
	private LocalDateTime fromDt;
	private LocalDateTime toDt;

	public Question converTnEntity() {
		Question.QuestionBuilder builder = Question.builder();
		if (!StringUtils.isEmpty(this.title)) {
			builder.title(this.title);
		}
		if (!StringUtils.isEmpty(this.url)) {
			builder.url(this.url);
		}
		if (!StringUtils.isEmpty(this.fromSource)) {
			builder.fromSource(this.fromSource);
		}
		if (!StringUtils.isEmpty(this.questionType)) {
			builder.questionType(this.questionType);
		}
		if (this.reviewCount > 0) {
			builder.reviewCount(this.reviewCount);
		}
		if (!StringUtils.isEmpty(this.content)) {
			builder.content(this.content);
		}
		return builder.build();
	}

	public void bindSearchFrom() {
		if (StringUtils.isEmpty(this.searchTerm))
			return;
		this.title = searchTerm;
		this.fromSource = searchTerm;
		this.questionType = searchTerm;
	}

	public String getEmail() {
		return super.getEmail();
	}

	public void setEmail(String email) {
		super.setEmail(email);
	}
}
