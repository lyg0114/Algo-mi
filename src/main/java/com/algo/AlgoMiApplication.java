package com.algo;

import com.algo.model.entity.UserInfo;
import com.algo.repository.UserInfoRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class AlgoMiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlgoMiApplication.class, args);
  }

  @Bean
  public CommandLineRunner initData(
      UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder
  ) {
    return args -> {
      userInfoRepository.saveAll(List.of(
          UserInfo.builder().userId(1L).userName("user-1").email("user-1@example.com")
              .passwd(passwordEncoder.encode("password")).role("ROLE_USER").build(),
          UserInfo.builder().userId(2L).userName("user-2").email("user-2@example.com")
              .passwd(passwordEncoder.encode("password")).role("ROLE_USER").build(),
          UserInfo.builder().userId(3L).userName("kyle").email("user@example.com")
              .passwd(passwordEncoder.encode("password")).role("ROLE_USER").build())
      );
    };
  }
}


