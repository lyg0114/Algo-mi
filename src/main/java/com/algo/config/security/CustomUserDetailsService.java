package com.algo.config.security;

import com.algo.model.entity.UserInfo;
import com.algo.repository.UserInfoRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserInfoRepository userInfoRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserInfo userInfo = this.userInfoRepository.findUserInfoByEmail(email);
    if (userInfo == null) {
      throw new UsernameNotFoundException("username " + email + " is not found");
    }
    return new CustomUserDetails(userInfo);
  }

  static final class CustomUserDetails extends CustomUser implements UserDetails {

    private final List<GrantedAuthority> ROLE;

    public CustomUserDetails(UserInfo userInfo) {
      super(userInfo.getUserId(), userInfo.getEmail(), userInfo.getPasswd());
      ROLE = Collections.unmodifiableList(
          AuthorityUtils.createAuthorityList(userInfo.getRole())
      );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return ROLE;
    }

    @Override
    public String getUsername() {
      return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }
  }
}
