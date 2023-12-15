package com.algo;

import com.algo.model.entity.Question;
import com.algo.model.entity.UserInfo;
import com.algo.repository.QuestionRepository;
import com.algo.repository.UserInfoRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class AlgoMiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlgoMiApplication.class, args);
  }

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
                .fromSource("leetcode")
                .userInfo(UserInfo.builder().userId(1L).userName("kyle").email("user@example.com")
                    .passwd(passwordEncoder.encode("password")).role("ROLE_USER").build())
                .build()
        );
      }
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}


