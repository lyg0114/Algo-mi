package com.sportedu.config.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final CustomUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    CustomUser customUser = this.userRepository.findCustomUserByEmail(username);
    if (customUser == null) {
      throw new UsernameNotFoundException("username " + username + " is not found");
    }
    return new CustomUserDetails(customUser);
  }

  static final class CustomUserDetails extends CustomUser implements UserDetails {

    private static final List<GrantedAuthority> ROLE_USER = Collections
        .unmodifiableList(
            AuthorityUtils.createAuthorityList("ROLE_USER")
        );

    CustomUserDetails(CustomUser customUser) {
      super(customUser.getId(), customUser.getEmail(), customUser.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return ROLE_USER;
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
