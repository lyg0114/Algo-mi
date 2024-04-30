package com.algo.auth.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author : iyeong-gyo
 * @package : com.algo.config.security
 * @since : 03.12.23
 */
@Profile("test")
@Component
public class AuthenticationUtilMcok implements AuthenticationUtil {

  public String getEmail() {
    return "user@example.com";
  }
}
