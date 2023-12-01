package com.algo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.algo.model.dto.QuestionDto;
import com.algo.model.entity.Question;
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
@Transactional
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

  @Test
  public void fidQuestionById_Test() {
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

  @Test
  public void findPaginatedForQuestionsBy_URL_Test() {
    //given
    SampleData.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    //when
    Page<QuestionDto> pageQuestion = questionService.findPaginatedForQuestions(
        QuestionDto.builder().url("http://localhost/leetcode/url/1").build(),
        PageRequest.of(0, 10)
    );
    //then
    List<QuestionDto> questions = pageQuestion.getContent();
    assertThat(questions).isNotNull();
    assertThat(questions).hasSize(1);
    questions.forEach(
        question -> assertThat(
            question.getUrl()).isEqualTo("http://localhost/leetcode/url/1"
        ));
  }

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

  @Test
  public void findPaginatedForQuestionsBy_ReviewCount_Test() {
    //given
    SampleData.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
    //when
    Page<QuestionDto> pageQuestion = questionService.findPaginatedForQuestions(
        QuestionDto.builder().reviewCount(4).build(),
        PageRequest.of(0, 10)
    );
    List<QuestionDto> questions = pageQuestion.getContent();
    //then
    assertThat(questions).isNotNull();
    assertThat(questions).hasSize(1);
    questions.forEach(
        question -> assertThat(question.getReviewCount()).isEqualTo(4)
    );
  }
}