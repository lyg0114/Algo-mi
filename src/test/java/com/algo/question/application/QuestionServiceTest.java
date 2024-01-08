package com.algo.question.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionRepository;
import com.algo.question.dto.QuestionResponse;
import com.algo.question.sample.QuestionSample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
}