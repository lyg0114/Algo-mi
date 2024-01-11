package com.algo.common.config;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import com.algo.question.domain.Question;
import com.algo.question.domain.QuestionRepository;
import java.util.List;
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
      UserInfoRepository userInfoRepository,
      QuestionRepository questionRepository,
      PasswordEncoder passwordEncoder
  ) {
    return args -> {
      userInfoRepository.saveAll(
          List.of(UserInfo.builder().userId(1L).userName("kyle").email("user@example.com")
              .passwd(passwordEncoder.encode("password")).role("USER").build()));

      for (int i = 0; i < 20; i++) {
        questionRepository.save(
            Question.builder()
                .title("title-" + i)
                .url("http://sample-url/" + i)
                .reviewCount(3)
                .fromSource("LEETCODE")
                .questionType("GREEDY")
                .userInfo(UserInfo.builder().userId(1L).userName("kyle").email("user@example.com")
                    .passwd(passwordEncoder.encode("password")).role("ROLE_USER").build())
                .build()
        );
      }
    };
  }
}
