package com.algo.auth.application;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.security
 * @ref :
 * https://github.com/spring-projects/spring-security-samples/tree/main/servlet/spring-boot/java/authentication/username-password
 * @since : 07.11.23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserInfoRepository userInfoRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserInfo userInfo = this.userInfoRepository.findUserInfoByEmail(email);
    return org.springframework.security.core.userdetails.User.builder()
        .username(userInfo.getEmail())
        .password(userInfo.getPasswd())
        .roles(userInfo.getRole())
        .build();
  }
}
