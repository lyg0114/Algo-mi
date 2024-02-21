package com.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author : iyeong-gyo
 * @package : com.study
 * @since : 21.02.24
 */
public class PasswordEncodingTest {

  @Test
  void passwordEncodingTest() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String pw1 = encoder.encode("password");
    String pw2 = encoder.encode("password");
    assertThat(pw1).isNotEqualTo(pw2);
  }

  @Test
  void passworEqualTest() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String pw = encoder.encode("password");
    assertThat(encoder.matches("password", pw)).isTrue();
  }
}
