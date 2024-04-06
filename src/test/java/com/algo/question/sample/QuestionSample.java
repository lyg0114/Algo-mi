package com.algo.question.sample;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionRepository;
import java.time.LocalDateTime;
import java.util.List;

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
        UserInfo.builder().userId(1L).userName("user-1").email("user-1@example.com").passwd("passowrd-1").role("ROLE_USER").isActivate(true).build(),
        UserInfo.builder().userId(2L).userName("user-2").email("user-2@example.com").passwd("passowrd-2").role("ROLE_USER").isActivate(true).build(),
        UserInfo.builder().userId(3L).userName("kyle").email("user@example.com").passwd("passowrd-3").role("ROLE_USER").isActivate(true).build())
    );

    // 테스트별로 userId가 갱신되어 저장되므로 email을 통해 조회하도록 처리
    // ex) A 테스트 진행 -> userId(1, 2, 3) -> rollback -> userId(4, 5, 6) -> B 테스트 진행
    UserInfo user1 = userInfoRepository.findUserInfoByEmailAndIsActivateTrue("user-1@example.com").get();
    UserInfo user2 = userInfoRepository.findUserInfoByEmailAndIsActivateTrue("user-2@example.com").get();
    questionRepository.saveAll(
        List.of(
            Question.builder().content("content-1").questionId(1L).questionType("dfs").title("title-1").url("http://localhost/leetcode/url/1").fromSource("백준").reviewCount(4).userInfo(user1).build(),
            Question.builder().content("content-2").questionId(2L).questionType("bfs").title("미로찾기-2").url("http://localhost/leetcode/url/2").fromSource("백준").reviewCount(0).userInfo(user1).build(),
            Question.builder().content("content-3").questionId(3L).questionType("greedy").title("title-3").url("http://localhost/leetcode/url/3").fromSource("백준").reviewCount(0).userInfo(user1).build(),
            Question.builder().content("content-4").questionId(4L).questionType("greedy").title("title-4").url("http://localhost/leetcode/url/4").fromSource("백준").reviewCount(0).userInfo(user2).build(),
            Question.builder().content("content-5").questionId(5L).questionType("dp").title("title-5").url("http://localhost/leetcode/url/5").fromSource("leetcode").reviewCount(0).userInfo(user2).build(),
            Question.builder().content("content-6").questionId(6L).questionType("bfs").title("title-6").url("http://localhost/leetcode/url/6").fromSource("leetcode").reviewCount(0).userInfo(user2).build()
        )
    );
  }

  public static void createSampleQuestionsForRecovery(
      QuestionRepository questionRepository, UserInfoRepository userInfoRepository
  ) {
    userInfoRepository.saveAll(List.of(
        UserInfo.builder().userId(1L).userName("user-1").email("user-1@example.com").passwd("passowrd-1").role("ROLE_USER").isActivate(true).build(),
        UserInfo.builder().userId(2L).userName("user-2").email("user-2@example.com").passwd("passowrd-2").role("ROLE_USER").isActivate(true).build(),
        UserInfo.builder().userId(3L).userName("user-3").email("user-3@example.com").passwd("passowrd-3").role("ROLE_USER").isActivate(true).build())
    );

    UserInfo user1 = userInfoRepository.findUserInfoByEmailAndIsActivateTrue("user-1@example.com").get();
    UserInfo user2 = userInfoRepository.findUserInfoByEmailAndIsActivateTrue("user-2@example.com").get();
    UserInfo user3 = userInfoRepository.findUserInfoByEmailAndIsActivateTrue("user-3@example.com").get();


    // createdDt 최초 저장할때는 무조건 현재시간으로 저장되므로 다시 조회해서 업데이트 해주는 로직
    Iterable<Question> questions = questionRepository.saveAll(
        List.of(
            Question.builder().content("content-1").questionId(1L).questionType("dfs") .title("title-1").url("http://localhost/leetcode/url/1").fromSource("백준").reviewCount(4).userInfo(user1).build(),
            Question.builder().content("content-2").questionId(2L).questionType("bfs").title("미로찾기-2").url("http://localhost/leetcode/url/2").fromSource("백준").reviewCount(0).userInfo(user1).build(),
            Question.builder().content("content-3").questionId(3L).questionType("greedy").title("title-3").url("http://localhost/leetcode/url/3").fromSource("백준").reviewCount(0).userInfo(user2).build(),
            Question.builder().content("content-4").questionId(4L).questionType("greedy").title("title-4").url("http://localhost/leetcode/url/4").fromSource("백준").reviewCount(0).userInfo(user2).build(),
            Question.builder().content("content-5").questionId(5L).questionType("dp").title("title-5").url("http://localhost/leetcode/url/5").fromSource("leetcode").reviewCount(0).userInfo(user3).build(),
            Question.builder().content("content-6").questionId(6L).questionType("bfs").title("title-6").url("http://localhost/leetcode/url/6").fromSource("leetcode").reviewCount(0).userInfo(user3).build(),
            Question.builder().content("content-7").questionId(7L).questionType("bfs").title("title-7").url("http://localhost/leetcode/url/7").fromSource("leetcode").reviewCount(0).userInfo(user3).build()
        ));
    for (Question question : questions) {
      question.setCreatedDt(LocalDateTime.now().minusDays(2L));
      questionRepository.save(question);
    }
  }
}
