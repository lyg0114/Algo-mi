package com.algo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.algo.mock.security.WithMockCustomUser;
import com.algo.model.dto.QuestionDto;
import com.algo.model.entity.Question;
import com.algo.repository.QuestionRepository;
import com.algo.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 01.12.23
 */
@Transactional
@SpringBootTest
class QuestionServiceCRUDTest {

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
    SampleData.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    QuestionDto questionDto = questionService
        .findPaginatedForQuestions(null, PageRequest.of(0, 1))
        .getContent()
        .get(0);
    long targetId = questionDto.getId();
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
    SampleData.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    QuestionDto questionDto = questionService
        .findPaginatedForQuestions(null, PageRequest.of(0, 1))
        .getContent()
        .get(0);
    long targetId = questionDto.getId();
    Question willUpdateQuestion = Question
        .builder()
        .title("Update new Question")
        .url("http://localhost/sample/url/7")
        .fromSource("Codility")
        .reviewCount(10)
        .build();
    //when
    QuestionDto updateQuestion = questionService.updateQuestion(targetId, willUpdateQuestion);
    //then
    assertThat(updateQuestion).isNotNull();
    assertThat(updateQuestion.getTitle()).isEqualTo(willUpdateQuestion.getTitle());
    assertThat(updateQuestion.getUrl()).isEqualTo(willUpdateQuestion.getUrl());
    assertThat(updateQuestion.getFromSource()).isEqualTo(willUpdateQuestion.getFromSource());
    assertThat(updateQuestion.getReviewCount()).isEqualTo(willUpdateQuestion.getReviewCount());
  }

  @WithMockCustomUser
  @Transactional
  @Test
  public void shouldInsertQuestion() {
    SampleData.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    //given
    QuestionDto questionDto = QuestionDto
        .builder()
        .title("Add new Question")
        .url("http://localhost/sample/url/1")
        .fromSource("leetcode")
        .reviewCount(5)
        .build();
    //when
    QuestionDto savedQuestionDto = questionService.addQuestion(questionDto);
    Long id = savedQuestionDto.getId();
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
    SampleData.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    Page<QuestionDto> pageQuestion = questionService.findPaginatedForQuestions(
        null, PageRequest.of(0, 10)
    );
    QuestionDto questionDto = pageQuestion.getContent().get(0);
    //when
    Question question = questionService.findQuestionById(questionDto.getId());
    //then
    assertThat(question).isNotNull();
    assertThat(question.getTitle()).isEqualTo(questionDto.getTitle());
    assertThat(question.getUrl()).isEqualTo(questionDto.getUrl());
    assertThat(question.getFromSource()).isEqualTo(questionDto.getFromSource());
    assertThat(question.getReviewCount()).isEqualTo(questionDto.getReviewCount());
  }
}