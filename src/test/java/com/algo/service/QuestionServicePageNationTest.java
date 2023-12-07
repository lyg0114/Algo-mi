package com.algo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.algo.model.dto.PageResponseDto;
import com.algo.model.dto.QuestionDto;
import com.algo.repository.QuestionRepository;
import com.algo.repository.UserInfoRepository;
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
class QuestionServicePageNationTest {

  @Autowired private QuestionService questionService;
  @Autowired protected QuestionRepository questionRepository;
  @Autowired protected UserInfoRepository userInfoRepository;

  @Test
  public void calculate_pagenation_study() {
    Page<QuestionDto> pageQuestion = questionService.findPaginatedForQuestions(
        null,
        PageRequest.of(1, 10)
    );
    for (QuestionDto questionDto : pageQuestion) {
      System.out.println("questionDto = " + questionDto);
    }

    System.out.println("pageQuestion.hasPrevious() = " + pageQuestion.hasPrevious());
    System.out.println("pageQuestion.hasNext() = " + pageQuestion.hasNext());
    System.out.println("pageQuestion.getTotalPages() = " + pageQuestion.getTotalPages());
    System.out.println("pageQuestion.getNumber() = " + pageQuestion.getNumber());
    System.out.println("startNumber = " + calculateStartNumber(pageQuestion.getNumber()));
    System.out.println("endNumber = " + calculateEndNumber(calculateStartNumber(pageQuestion.getNumber()), pageQuestion.getTotalPages())) ;
    System.out.println("pageQuestion.getTotalElements() = " + pageQuestion.getTotalElements());
    System.out.println("pageQuestion.getNumberOfElements() = " + pageQuestion.getNumberOfElements());
  }

  private int calculateStartNumber(int input) {
    int adjustedValue = (input / 10) * 10;
    return adjustedValue + 1;
  }

  private int calculateEndNumber(int start, int totalPage) {
    int endPage = start + 9;
    return Math.min(totalPage - 1, endPage);
  }

  @Test
  public void calculate_pageResponseDto_study() {
    Page<QuestionDto> pageQuestion = questionService.findPaginatedForQuestions(
        null,
        PageRequest.of(1, 10)
    );
    PageResponseDto<QuestionDto> pageResponseDto = PageResponseDto.of(pageQuestion, 10);

    for (int i = 0; i < pageResponseDto.getSize(); i++) {
      assertThat(pageQuestion.getContent().get(i).toString())
          .isEqualTo(pageResponseDto.getContent().get(i).toString());
    }
    assertThat(pageQuestion).isNotNull();
    assertThat(pageResponseDto).isNotNull();
    assertThat(pageQuestion.hasPrevious()).isEqualTo(pageResponseDto.hasPrevious());
    assertThat(pageQuestion.hasNext()).isEqualTo(pageResponseDto.hasNext());
    assertThat(pageQuestion.getTotalPages()).isEqualTo(pageResponseDto.getTotalPages());
    assertThat(pageQuestion.getNumber()).isEqualTo(pageResponseDto.getNumber());
    assertThat(pageQuestion.getTotalElements()).isEqualTo(pageResponseDto.getTotalElements());
    assertThat(pageQuestion.getNumberOfElements()).isEqualTo(pageResponseDto.getNumberOfElements());

    assertThat(calculateStartNumber(pageQuestion.getNumber()))
        .isEqualTo(pageResponseDto.getStartNumber());
    assertThat(calculateEndNumber(calculateStartNumber(pageQuestion.getNumber()),
        pageQuestion.getTotalPages()))
        .isEqualTo(pageResponseDto.getEndNumber());
  }
}