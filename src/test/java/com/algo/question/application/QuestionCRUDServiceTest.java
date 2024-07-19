package com.algo.question.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.algo.auth.domain.UserInfoRepository;
import com.algo.common.dto.UserInfoRequest;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionRepository;
import com.algo.question.dto.QuestionRequest;
import com.algo.question.dto.QuestionResponse;
import com.algo.question.sample.QuestionSample;

/**
 * @author : iyeong-gyo
 * @package : com.algo.question.application
 * @since : 08.01.24
 */
@ActiveProfiles("test")
@DisplayName("문제 조회, 등록, 수정, 삭제 테스트")
@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class QuestionCRUDServiceTest {

	@Autowired QuestionService questionService;
	@Autowired QuestionRepository questionRepository;
	@Autowired UserInfoRepository userInfoRepository;

	@DisplayName("문제 삭제")
	@Test
	void shouldDeleteQuestion() {
		//given
		QuestionSample.createSamplefindPaginatedForQuestionsV1(questionRepository, userInfoRepository);
		QuestionRequest questionRequest = new QuestionRequest();
		questionRequest.setEmail("user-1@example.com");
		QuestionResponse questionResponse = questionService
			.findPaginatedForQuestions(questionRequest, PageRequest.of(0, 1))
			.getContent()
			.get(0);
		long targetId = questionResponse.getId();

		//when
		questionService.deleteQuestion(targetId, new UserInfoRequest("user-1@example.com"));

		//then
		Question targetQuestion;
		try {
			targetQuestion = questionService.findQuestionById(targetId);
		} catch (Exception e) {
			targetQuestion = null;
		}
		assertThat(targetQuestion).isNull();
	}

	@DisplayName("문제 수정")
	@Test
	void shouldUpdateQuestion() {
		//given
		QuestionSample.createSamplefindPaginatedForQuestionsV1(questionRepository, userInfoRepository);
		QuestionRequest questionRequest = new QuestionRequest();
		String targetEmail = "user-1@example.com";
		questionRequest.setEmail(targetEmail);
		long targetId = questionService
			.findPaginatedForQuestions(questionRequest, PageRequest.of(0, 1))
			.getContent()
			.get(0)
			.getId();
		QuestionRequest willUpdateQuestionDto = QuestionRequest
			.builder()
			.title("Update new Question")
			.questionType("백준")
			.url("http://localhost/sample/url/7")
			.fromSource("Codility")
			.reviewCount(10)
			.content("### update contnet")
			.build();
		willUpdateQuestionDto.setEmail(targetEmail);

		//when
		QuestionResponse updateResponse = questionService.updateQuestion(targetId, willUpdateQuestionDto);

		//then
		assertThat(updateResponse).isNotNull();
		assertThat(updateResponse.getTitle()).isEqualTo(willUpdateQuestionDto.getTitle());
		assertThat(updateResponse.getUrl()).isEqualTo(willUpdateQuestionDto.getUrl());
		assertThat(updateResponse.getFromSource()).isEqualTo(willUpdateQuestionDto.getFromSource());
		assertThat(updateResponse.getReviewCount()).isEqualTo(willUpdateQuestionDto.getReviewCount());
		assertThat(updateResponse.getContent()).isEqualTo(willUpdateQuestionDto.getContent());
	}

	@DisplayName("문제 등록")
	@Test
	void shouldInsertQuestion() {
		QuestionSample.createSamplefindPaginatedForQuestionsV1(questionRepository, userInfoRepository);
		//given
		QuestionRequest questionDto = QuestionRequest
			.builder()
			.title("Add new Question")
			.questionType("백준")
			.url("http://localhost/sample/url/1")
			.fromSource("leetcode")
			.reviewCount(5)
			.content("### content")
			.build();

		//when
		String email = "user@example.com";
		questionDto.setEmail(email);
		QuestionResponse savedResponse = questionService.addQuestion(questionDto);
		Long id = savedResponse.getId();
		Question byId = questionRepository.findById(id).get();

		//then
		assertThat(byId).isNotNull();
		assertThat(byId.getTitle()).isEqualTo(questionDto.getTitle());
		assertThat(byId.getUrl()).isEqualTo(questionDto.getUrl());
		assertThat(byId.getFromSource()).isEqualTo(questionDto.getFromSource());
		assertThat(byId.getReviewCount()).isEqualTo(questionDto.getReviewCount());
		assertThat(byId.getUserInfo().getEmail()).isEqualTo("user@example.com");
		assertThat(byId.getUserInfo().getUserName()).isEqualTo("kyle");
		assertThat(byId.getContent()).isEqualTo("### content");
	}

	@DisplayName("문제 단건 조회")
	@Test
	void shouldFindQuestion() {
		//given
		QuestionSample.createSamplefindPaginatedForQuestionsV1(questionRepository, userInfoRepository);
		QuestionRequest questionRequest = new QuestionRequest();
		questionRequest.setEmail("user-1@example.com");
		Page<QuestionResponse> responses = questionService.findPaginatedForQuestions(
			questionRequest, PageRequest.of(0, 10)
		);
		QuestionResponse questionResponse = responses.getContent().get(0);

		//when
		Question question = questionService.findQuestionById(questionResponse.getId());

		//then
		assertThat(question).isNotNull();
		assertThat(question.getTitle()).isEqualTo(questionResponse.getTitle());
		assertThat(question.getUrl()).isEqualTo(questionResponse.getUrl());
		assertThat(question.getFromSource()).isEqualTo(questionResponse.getFromSource());
		assertThat(question.getReviewCount()).isEqualTo(questionResponse.getReviewCount());
		assertThat(question.getContent()).isEqualTo(questionResponse.getContent());
	}
}