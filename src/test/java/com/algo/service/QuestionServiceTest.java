package com.algo.service;

import com.algo.repository.UserInfoRepository;
import com.algo.repository.querydsl.QuestionCustomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
  private QuestionCustomRepository questionCustomRepository;
  @Autowired
  private UserInfoRepository userInfoRepository;

  @Test
  public void findPaginatedForQuestionsTest() {

  }


}