package com.algo.service;

import com.algo.model.entity.Question;
import com.algo.model.entity.UserInfo;
import com.algo.repository.QuestionRepository;
import com.algo.repository.UserInfoRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
  private QuestionRepository questionRepository;
  @Autowired
  private UserInfoRepository userInfoRepository;

  @BeforeEach
  void before() {
    questionRepository.deleteAll();
    userInfoRepository.deleteAll();
  }

  @Transactional
  @Test
  public void findPaginatedForQuestionsTest() {
    SampleData.createSamplefindPaginatedForQuestionsTest(questionRepository, userInfoRepository);
  }

  static class SampleData {
    public static void createSamplefindPaginatedForQuestionsTest(
        QuestionRepository questionRepository, UserInfoRepository userInfoRepository
    ) {
      userInfoRepository.saveAll(List.of(
          UserInfo.builder().userName("user-1").email("user-1@example.com").passwd("passowrd-1").build(),
          UserInfo.builder().userName("user-2").email("user-2@example.com").passwd("passowrd-2").build())
      );
      UserInfo user1 = userInfoRepository.findById(1L).get();
      UserInfo user2 = userInfoRepository.findById(2L).get();
      questionRepository.saveAll(
          List.of(
              Question.builder().title("titld-1").url("http://localhost/leetcode/url").fromSource("leetCode").reviewCount(0).userInfo(user1).build(),
              Question.builder().title("titld-2").url("http://localhost/leetcode/url").fromSource("leetCode").reviewCount(0).userInfo(user1).build(),
              Question.builder().title("titld-3").url("http://localhost/leetcode/url").fromSource("leetCode").reviewCount(0).userInfo(user1).build(),
              Question.builder().title("titld-4").url("http://localhost/leetcode/url").fromSource("leetCode").reviewCount(0).userInfo(user2).build(),
              Question.builder().title("titld-5").url("http://localhost/leetcode/url").fromSource("leetCode").reviewCount(0).userInfo(user2).build(),
              Question.builder().title("titld-6").url("http://localhost/leetcode/url").fromSource("leetCode").reviewCount(0).userInfo(user2).build()
          )
      );
    }
  }
}