package com.algo.mock.sample;

import com.algo.model.entity.Question;
import com.algo.model.entity.UserInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : iyeong-gyo
 * @package : com.algo.mock.sample
 * @since : 21.11.23
 */
public class CreateSampleData {

  public static List<Question> makeSampleQuestion() {
    List<Question> questions = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      questions.add(
          Question.builder()
              .questionId((long) i)
              .title("title - " + i)
              .fromSource("leetcode")
              .url("https://leetcode/" + i)
              .reviewCount(random.nextInt(9))
              .userInfo(UserInfo.builder()
                  .userId(2L)
                  .build()
              )
              .build()
      );
    }
    return questions;
  }

  public static List<UserInfo> makeSampleUserInfo() {
    List<UserInfo> userinfos = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      userinfos.add(
          UserInfo.builder()
              .userId((long) i)
              .email("sample" + i + "@example.com")
              .userName("sample" + i)
              .passwd("password" + i)
              .build()
      );
    }
    return userinfos;
  }
}
