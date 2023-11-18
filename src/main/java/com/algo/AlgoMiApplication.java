package com.algo;

import com.algo.config.security.CustomUser;
import com.algo.config.security.MapCustomUserRepository;
import jakarta.persistence.PrePersist;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AlgoMiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AlgoMiApplication.class, args);
  }

  @Bean
  MapCustomUserRepository userRepository() {
    // the hashed password was calculated using the following code
    // the hash should be done up front, so malicious users cannot discover the
    // password
    // PasswordEncoder encoder =
    // PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // String encodedPassword = encoder.encode("password");
    // the raw password is "password"
    String encodedPassword = "{bcrypt}$2a$10$h/AJueu7Xt9yh3qYuAXtk.WZJ544Uc2kdOKlHu2qQzCh/A3rq46qm";

    Map<String, CustomUser> emailToCustomUser = new HashMap<>();
    CustomUser customUser1 = new CustomUser(1L, "user@example.com", encodedPassword);
    CustomUser customAdmin1 = new CustomUser(2L, "admin@example.com", encodedPassword);
    emailToCustomUser.put(customUser1.getEmail(), customUser1);
    emailToCustomUser.put(customAdmin1.getEmail(), customAdmin1);
    return new MapCustomUserRepository(emailToCustomUser);
  }

}


