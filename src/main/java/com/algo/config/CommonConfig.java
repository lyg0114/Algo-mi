package com.algo.config;

import com.algo.model.entity.Question;
import com.algo.model.entity.UserInfo;
import com.algo.repository.QuestionRepository;
import com.algo.repository.UserInfoRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

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

  @Profile("dev")
  @Bean
  public CommandLineRunner initData(
      UserInfoRepository userInfoRepository,
      QuestionRepository questionRepository,
      PasswordEncoder passwordEncoder
  ) {
    return args -> {
      userInfoRepository.saveAll(
          List.of(UserInfo.builder().userId(1L).userName("kyle").email("user@example.com")
              .passwd(passwordEncoder.encode("password")).role("ROLE_USER").build()));

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
