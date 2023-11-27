package com.algo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.algo.model.dto.QuestionDto;
import com.algo.repository.QuestionRepository;
import com.algo.repository.UserInfoRepository;
import java.util.List;
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
 * @since : 27.11.23
 */
@SpringBootTest
class QuestionServiceTest {

  @Autowired
  private QuestionService questionService;
  @Autowired
  protected QuestionRepository questionRepository;
  @Autowired
  protected UserInfoRepository userInfoRepository;

  @BeforeEach
  void before() {
    questionRepository.deleteAll();
    userInfoRepository.deleteAll();
  }

  @Transactional
  @Test
  public void findPaginatedForQuestionsBy_Title_Test() {
    //given
    SampleData.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    //when
    Page<QuestionDto> pageQuestion = questionService.findPaginatedForQuestions(
        QuestionDto.builder().title("title-2").build(),
        PageRequest.of(0, 10)
    );
    List<QuestionDto> questions = pageQuestion.getContent();
    //then
    assertThat(questions).isNotNull();
    assertThat(questions).hasSize(1);
    assertThat(questions.get(0).getTitle()).isEqualTo("title-2");
  }

  @Transactional
  @Test
  public void findPaginatedForQuestionsBy_URL_Test() {
  }

  @Transactional
  @Test
  public void findPaginatedForQuestionsBy_FromSource_Test() {
    //given
    SampleData.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    //when
    Page<QuestionDto> pageQuestion = questionService.findPaginatedForQuestions(
        QuestionDto.builder().fromSource("leetCode").build(),
        PageRequest.of(0, 10)
    );
    List<QuestionDto> questions = pageQuestion.getContent();
    //then
    assertThat(questions).isNotNull();
    assertThat(questions).hasSize(3);
    questions.forEach(
        question -> assertThat(question.getFromSource()).isEqualTo("leetCode")
    );
  }

  @Transactional
  @Test
  public void findPaginatedForQuestionsBy_ReviewCount_Test() {
  }
}