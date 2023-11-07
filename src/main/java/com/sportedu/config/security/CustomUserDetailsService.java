package com.sportedu.config.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.security
 * @ref :
 * https://github.com/spring-projects/spring-security-samples/tree/main/servlet/spring-boot/java/authentication/username-password
 * @since : 07.11.23
 */
public class CustomUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = null;
    return user;
  }
}
