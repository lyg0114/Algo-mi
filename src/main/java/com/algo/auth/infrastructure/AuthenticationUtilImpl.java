package com.algo.auth.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author : iyeong-gyo
 * @package : com.algo.config.security
 * @since : 03.12.23
 */
@Profile("!test")
@Component
public class AuthenticationUtilImpl implements AuthenticationUtil {

  public String getEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }
}
