package com.algo.mock.sample;

import com.algo.model.entity.Question;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.algo.mock.sample
 * @since : 21.11.23
 */
public class CreateSampleQuestions {

  public static List<Question> makeSampleQuestion() {
    List<Question> questions = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      questions.add(
          Question
              .builder()
              .questionId((long) i)
              .title("title - " + i)
              .fromSource("leetcode")
              .url("https://leetcode/" + i)
              .reviewCount(i % 10)
              .build()
      );
    }
    return questions;
  }
}
