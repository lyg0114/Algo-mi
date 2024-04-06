package com.algo.auth.application;

import com.algo.auth.domain.UserInfo;
import com.algo.auth.domain.UserInfoRepository;
import java.util.NoSuchElementException;
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
    UserInfo userInfo = userInfoRepository.findUserInfoByEmailAndIsActivateTrue(email)
        .orElseThrow(() -> new NoSuchElementException("회원정보를 찾을 수 없습니다."));
    return org.springframework.security.core.userdetails.User.builder()
        .username(userInfo.getEmail())
        .password(userInfo.getPasswd())
        .roles(userInfo.getRole())
        .build();
  }
}
