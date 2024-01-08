package com.algo.question.sample;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionRepository;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.algo.question.sample
 * @since : 08.01.24
 */
public class QuestionSample {

  public static void createSamplefindPaginatedForQuestionsTest(
      QuestionRepository questionRepository, UserInfoRepository userInfoRepository
  ) {
    userInfoRepository.saveAll(List.of(
        UserInfo.builder().userId(1L).userName("user-1").email("user-1@example.com").passwd("passowrd-1").role("ROLE_USER").build(),
        UserInfo.builder().userId(2L).userName("user-2").email("user-2@example.com").passwd("passowrd-2").role("ROLE_USER").build(),
        UserInfo.builder().userId(3L).userName("kyle").email("user@example.com").passwd("passowrd-3").role("ROLE_USER").build())
    );
    List<UserInfo> userInfos = userInfoRepository.findUserInfos();
    UserInfo user1 = userInfos.get(0);
    UserInfo user2 = userInfos.get(1);
    questionRepository.saveAll(
        List.of(
            Question.builder().questionId(1L).questionType("백준").title("title-1").url("http://localhost/leetcode/url/1").fromSource("leetCode").reviewCount(4).userInfo(user1).build(),
            Question.builder().questionId(2L).questionType("백준").title("title-2").url("http://localhost/leetcode/url/2").fromSource("leetCode").reviewCount(0).userInfo(user1).build(),
            Question.builder().questionId(3L).questionType("백준").title("title-3").url("http://localhost/leetcode/url/3").fromSource("leetCode").reviewCount(0).userInfo(user1).build(),
            Question.builder().questionId(4L).questionType("백준").title("title-4").url("http://localhost/leetcode/url/4").fromSource("Codility").reviewCount(0).userInfo(user2).build(),
            Question.builder().questionId(5L).questionType("leet-code").title("title-5").url("http://localhost/leetcode/url/5").fromSource("Codility").reviewCount(0).userInfo(user2).build(),
            Question.builder().questionId(6L).questionType("leet-code").title("title-6").url("http://localhost/leetcode/url/6").fromSource("Codility").reviewCount(0).userInfo(user2).build()
        )
    );
  }

}
