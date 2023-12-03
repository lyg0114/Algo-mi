package com.algo.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author : iyeong-gyo
 * @package : com.algo.config.security
 * @since : 03.12.23
 */
public class AuthenticationUtil {

  public static String getUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }
}
