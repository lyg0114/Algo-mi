package com.algo.question.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionRepository;
import com.algo.question.dto.QuestionRequest;
import com.algo.question.dto.QuestionResponse;
import com.algo.question.sample.QuestionSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.question.application
 * @since : 08.01.24
 */
@Transactional
@SpringBootTest
class QuestionServiceTest {

  @Autowired private QuestionService questionService;
  @Autowired protected QuestionRepository questionRepository;
  @Autowired protected UserInfoRepository userInfoRepository;

  @BeforeEach
  void before() {
    questionRepository.deleteAll();
    userInfoRepository.deleteAll();
  }

  @Transactional
  @Test
  public void shouldDeleteQuestion() {
    //given
    QuestionSample.createSamplefindPaginatedForQuestionsTest(questionRepository,
        userInfoRepository);
    QuestionResponse questionResponse = questionService
        .findPaginatedForQuestions(null, PageRequest.of(0, 1))
        .getContent()
        .get(0);
    long targetId = questionResponse.getId();
    //when
    questionService.deleteQuestion(targetId);
    //then
    Question targetQuestion;
    try {
      targetQuestion = questionService.findQuestionById(targetId);
    } catch (Exception e) {
      targetQuestion = null;
    }
    assertThat(targetQuestion).isNull();
  }

  @Transactional
  @Test
  public void shouldUpdateQuestion() {
    //given
    QuestionSample.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    long targetId = questionService
        .findPaginatedForQuestions(null, PageRequest.of(0, 1))
        .getContent()
        .get(0)
        .getId()
        ;
    QuestionRequest willUpdateQuestionDto = QuestionRequest
        .builder()
        .title("Update new Question")
        .questionType("백준")
        .url("http://localhost/sample/url/7")
        .fromSource("Codility")
        .reviewCount(10)
        .build();
    //when
    QuestionResponse updateResponse = questionService.updateQuestion(targetId, willUpdateQuestionDto);
    //then
    assertThat(updateResponse).isNotNull();
    assertThat(updateResponse.getTitle()).isEqualTo(willUpdateQuestionDto.getTitle());
    assertThat(updateResponse.getUrl()).isEqualTo(willUpdateQuestionDto.getUrl());
    assertThat(updateResponse.getFromSource()).isEqualTo(willUpdateQuestionDto.getFromSource());
    assertThat(updateResponse.getReviewCount()).isEqualTo(willUpdateQuestionDto.getReviewCount());
  }

  @Transactional
  @Test
  public void shouldInsertQuestion() {
    QuestionSample.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    //given
    QuestionRequest questionDto = QuestionRequest
        .builder()
        .title("Add new Question")
        .questionType("백준")
        .url("http://localhost/sample/url/1")
        .fromSource("leetcode")
        .reviewCount(5)
        .build();
    //when
    String email = "user@example.com";
    QuestionResponse savedResponse = questionService.addQuestion(email, questionDto);
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
  }

  @Test
  public void shouldFindQuestion() {
    //given
    QuestionSample.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    Page<QuestionResponse> responses = questionService.findPaginatedForQuestions(
        null, PageRequest.of(0, 10)
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
  }
}