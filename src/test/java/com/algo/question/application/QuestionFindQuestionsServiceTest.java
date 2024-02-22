package com.algo.question.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionRepository;
import com.algo.question.dto.QuestionResponse;
import com.algo.question.sample.QuestionSample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.question.application
 * @since : 08.01.24
 */
@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class QuestionFindQuestionsServiceTest {

  @Autowired private QuestionService questionService;
  @Autowired protected QuestionRepository questionRepository;
  @Autowired protected UserInfoRepository userInfoRepository;

  @Test
  public void shouldFindQuestion() {
    //given
    QuestionSample.createSamplefindPaginatedForQuestionsTest(questionRepository,
        userInfoRepository);
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