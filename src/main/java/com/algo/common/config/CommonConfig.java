package com.algo.common.config;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionRepository;
import java.util.List;
import java.util.Random;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * @author : iyeong-gyo
 * @package : com.algo.config
 * @since : 20.11.23
 */
@Configuration
public class CommonConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  // 샘플데이터로 구동시 주석 해제
  @Profile("local")
  @Bean
  public CommandLineRunner initData(
      UserInfoRepository userInfoRepository, QuestionRepository questionRepository,
      PasswordEncoder encoder
  ) {
    return args -> {
      userInfoRepository.saveAll(
          List.of(
              UserInfo.builder().userId(1L).userName("kyle").email("user@example.com")
                  .passwd(encoder.encode("password")).role("USER").build(),
              UserInfo.builder().userId(2L).userName("jhone").email("jhone@example.com")
                  .passwd(encoder.encode("password")).role("GUEST").build(),
              UserInfo.builder().userId(3L).userName("lizzy").email("lizzy@example.com")
                  .passwd(encoder.encode("password")).role("USER").build(),
              UserInfo.builder().userId(4L).userName("tom").email("tom@example.com")
                  .passwd(encoder.encode("password")).role("USER").build()
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
                    .passwd(encoder.encode("password")).role("ROLE_USER").build())
                .build()
        );
      }
    };
  }
}
