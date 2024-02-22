package com.algo.question.sample;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionRepository;
import java.util.List;
import java.util.Random;

/**
 * @author : iyeong-gyo
 * @package : com.algo.question.sample
 * @since : 08.01.24
 */
public class QuestionSample {

  public static void createSamplefindPaginatedForQuestionsV1(
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

  public static void createSamplefindPaginatedForQuestionsV2(
      QuestionRepository questionRepository, UserInfoRepository userInfoRepository
  ) {
    userInfoRepository.saveAll(
        List.of(
            UserInfo.builder().userId(1L).userName("kyle").email("user@example.com")
                .passwd("password").role("USER").build(),
            UserInfo.builder().userId(2L).userName("jhone").email("jhone@example.com")
                .passwd("password").role("GUEST").build(),
            UserInfo.builder().userId(3L).userName("lizzy").email("lizzy@example.com")
                .passwd("password").role("USER").build(),
            UserInfo.builder().userId(4L).userName("tom").email("tom@example.com")
                .passwd("password").role("USER").build()
        )
    );

    Random random = new Random();
    List<String> fromSources
        = List.of("LEETCODE", "HACKERRANK", "CODILITY", "BAEKJOON", "PROGRAMMERS");
    List<String> questionTypes = List.of("GREEDY", "DFS", "BFS");

    for (int i = 0; i < 50; i++) {
      questionRepository.save(
          Question.builder()
              .title("title-" + i)
              .url("http://sample-url/" + i)
              .reviewCount(3)
              .fromSource(fromSources.get(random.nextInt(fromSources.size())))
              .questionType(questionTypes.get(random.nextInt(questionTypes.size())))
              .userInfo(UserInfo.builder().userId(1L).userName("kyle").email("user@example.com")
                  .passwd("password").role("ROLE_USER").build())
              .build()
      );
    }
  }
}
