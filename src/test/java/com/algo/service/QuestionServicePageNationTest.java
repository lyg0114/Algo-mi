package com.algo.service;

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
    System.out.println("startPage = " + calculateStartPage(pageQuestion.getNumber()));
    System.out.println( "endPage = " + calculateEndPage(calculateStartPage(pageQuestion.getNumber()), pageQuestion.getTotalPages())) ;
    System.out.println("pageQuestion.getTotalElements() = " + pageQuestion.getTotalElements());
    System.out.println("pageQuestion.getNumberOfElements() = " + pageQuestion.getNumberOfElements());
  }

  private int calculateStartPage(int input) {
    int adjustedValue = (input / 10) * 10;
    return adjustedValue + 1;
  }

  private int calculateEndPage(int start, int totalPage) {
    int endPage = start + 9;
    return Math.min(totalPage - 1, endPage);
  }
}