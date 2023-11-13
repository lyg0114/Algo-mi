package com.algo.mock;

import com.algo.config.security.CustomUser;
import com.algo.config.security.CustomUserDetailsService;
import com.algo.config.security.CustomUserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.sporteduplatform
 * @since : 07.11.23
 */
public class WithMockCustomUserSecurityContextFactory implements
    WithSecurityContextFactory<WithMockCustomUser> {

  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
    String username = mockCustomUser.email();
    // a stub CustomUserRepository that returns the user defined in the annotation
    CustomUserRepository userRepository = (email) -> new CustomUser(mockCustomUser.id(), username, "");
    // CustomUserRepositoryUserDetailsService ensures our UserDetails is consistent
    // with our production application
    CustomUserDetailsService userDetailsService = new CustomUserDetailsService(userRepository);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
        userDetails, userDetails.getPassword(), userDetails.getAuthorities())
    );
    return securityContext;
  }
}
