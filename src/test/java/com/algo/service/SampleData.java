package com.algo.service;

import com.algo.model.entity.Question;
import com.algo.model.entity.UserInfo;
import com.algo.repository.QuestionRepository;
import com.algo.repository.UserInfoRepository;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.algo.service
 * @since : 27.11.23
 */
class SampleData {
  public static void createSamplefindPaginatedForQuestionsTest(
      QuestionRepository questionRepository, UserInfoRepository userInfoRepository
  ) {
    userInfoRepository.saveAll(List.of(
        UserInfo.builder().userName("user-1").email("user-1@example.com").passwd("passowrd-1").build(),
        UserInfo.builder().userName("user-2").email("user-2@example.com").passwd("passowrd-2").build())
    );
    List<UserInfo> userInfos = userInfoRepository.findUserInfos();
    UserInfo user1 = userInfos.get(0);
    UserInfo user2 = userInfos.get(1);
    questionRepository.saveAll(
        List.of(
            Question.builder().title("title-1").url("http://localhost/leetcode/url").fromSource("leetCode").reviewCount(0).userInfo(user1).build(),
            Question.builder().title("title-2").url("http://localhost/leetcode/url").fromSource("leetCode").reviewCount(0).userInfo(user1).build(),
            Question.builder().title("title-3").url("http://localhost/leetcode/url").fromSource("leetCode").reviewCount(0).userInfo(user1).build(),
            Question.builder().title("title-4").url("http://localhost/leetcode/url").fromSource("Codility").reviewCount(0).userInfo(user2).build(),
            Question.builder().title("title-5").url("http://localhost/leetcode/url").fromSource("Codility").reviewCount(0).userInfo(user2).build(),
            Question.builder().title("title-6").url("http://localhost/leetcode/url").fromSource("Codility").reviewCount(0).userInfo(user2).build()
        )
    );
  }
}
