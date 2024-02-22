package com.algo.question.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.QuestionRepository;
import com.algo.question.dto.QuestionRequest;
import com.algo.question.dto.QuestionResponse;
import com.algo.question.sample.QuestionSample;
import java.util.List;
import org.assertj.core.api.Assertions;
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

  @Autowired QuestionService questionService;
  @Autowired QuestionRepository questionRepository;
  @Autowired UserInfoRepository userInfoRepository;

  @Test
  public void shouldFindQuestion() {
    QuestionSample.createSamplefindPaginatedForQuestionsV1(questionRepository, userInfoRepository);
    QuestionRequest questionRequest = new QuestionRequest();
    questionRequest.setSearchTerm("미로");
    Page<QuestionResponse> responses = questionService.findPaginatedForQuestions(
        questionRequest, PageRequest.of(0, 12)
    );

    List<QuestionResponse> content = responses.getContent();
    assertThat(content.size()).isEqualTo(1);
    QuestionResponse response = content.get(0);
    assertThat(response.getTitle()).contains("미로");
  }
}