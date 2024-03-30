package com.algo.question.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.QuestionRepository;
import com.algo.question.dto.QuestionRequest;
import com.algo.question.dto.QuestionResponse;
import com.algo.question.sample.QuestionSample;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.algo.question.application
 * @since : 08.01.24
 */
@ActiveProfiles("test")
@DisplayName("문제조회 관련 테스트")
@Transactional
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class QuestionFindQuestionsServiceTest {

  @Autowired QuestionService questionService;
  @Autowired QuestionRepository questionRepository;
  @Autowired UserInfoRepository userInfoRepository;

  @DisplayName("사용자별 문제 조회")
  @Test
  public void shouldFindQuestionByUser() {
    QuestionSample.createSamplefindPaginatedForQuestionsV1(questionRepository, userInfoRepository);
    QuestionRequest questionRequest = new QuestionRequest();
    questionRequest.setEmail("user-1@example.com");
    Page<QuestionResponse> responses = questionService.findPaginatedForQuestions(questionRequest, PageRequest.of(0, 12));
    List<QuestionResponse> content = responses.getContent();
    System.out.println("content = " + content);
    assertThat(content.size()).isEqualTo(3);
    QuestionResponse response = content.get(0);
    assertThat(response.getTitle()).contains("title-6");
  }

  @DisplayName("조건에 따른 문제 조회")
  @Test
  public void shouldFindQuestionBySearchTerm() {
    QuestionSample.createSamplefindPaginatedForQuestionsV1(questionRepository, userInfoRepository);
    QuestionRequest questionRequest = new QuestionRequest();
    questionRequest.setSearchTerm("미로");
    Page<QuestionResponse> responses = questionService.findPaginatedForQuestions(questionRequest, PageRequest.of(0, 12));
    List<QuestionResponse> content = responses.getContent();
    assertThat(content.size()).isEqualTo(1);
    QuestionResponse response = content.get(0);
    assertThat(response.getTitle()).contains("미로");
  }

  @DisplayName("문제 조회")
  @Test
  public void shouldFindQuestions() {
    QuestionSample.createSamplefindPaginatedForQuestionsV1(questionRepository, userInfoRepository);
    QuestionRequest questionRequest = new QuestionRequest();
    Page<QuestionResponse> responses = questionService.findPaginatedForQuestions(questionRequest, PageRequest.of(0, 12));
    List<QuestionResponse> results = responses.getContent();
    assertThat(results.size()).isEqualTo(6);
    for (QuestionResponse result : results) {
      assertThat(result).isNotNull();
      assertThat(result.getId()).isNotNull();
      assertThat(result.getTitle()).isNotEmpty();
      assertThat(result.getQuestionType()).isNotEmpty();
      assertThat(result.getTitle()).isNotEmpty();
      assertThat(result.getUrl()).isNotEmpty();
      assertThat(result.getFromSource()).isNotEmpty();
      assertThat(result.getContent()).isNotEmpty();
      assertThat(result.getReviewCount()).isGreaterThan(-1);
    }
  }
}