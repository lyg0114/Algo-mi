package com.sportedu;

import com.sportedu.config.security.CustomUser;
import com.sportedu.config.security.MapCustomUserRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SportEduPlatformApplication {

  public static void main(String[] args) {
    SpringApplication.run(SportEduPlatformApplication.class, args);
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

    CustomUser customUser = new CustomUser(1L, "user@example.com", encodedPassword);
    Map<String, CustomUser> emailToCustomUser = new HashMap<>();
    emailToCustomUser.put(customUser.getEmail(), customUser);
    return new MapCustomUserRepository(emailToCustomUser);
  }
}
