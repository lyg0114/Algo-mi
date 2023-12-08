package com.algo.service;

import com.algo.repository.QuestionRepository;
import com.algo.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
  public void pagenation_test(){

  }
}