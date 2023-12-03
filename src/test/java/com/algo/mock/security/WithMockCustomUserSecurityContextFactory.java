package com.algo.mock.security;

import com.algo.config.security.CustomUserDetailsService;
import com.algo.model.entity.UserInfo;
import com.algo.repository.UserInfoRepository;
import java.util.List;
import java.util.Optional;
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
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
    String username = mockCustomUser.email();
    CustomUserDetailsService userDetailsService = new CustomUserDetailsService(getMockUserInfoRepository());
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
        userDetails, userDetails.getPassword(), userDetails.getAuthorities())
    );
    return securityContext;
  }

  private UserInfoRepository getMockUserInfoRepository() {
    return new UserInfoRepository() {
      @Override public UserInfo findUserInfoByEmail(String email) {
        return new UserInfo(3,"user@example.com","kyle","password","ROLE_USER");
      }
      @Override public List<UserInfo> findUserInfos() {
        return List.of(
            new UserInfo(3,"user@example.com","kyle","password","ROLE_USER")
        );
      }
      @Override public <S extends UserInfo> S save(S entity) {return null;}
      @Override public <S extends UserInfo> Iterable<S> saveAll(Iterable<S> entities) {return null;}
      @Override public Optional<UserInfo> findById(Long aLong) {return Optional.empty();}
      @Override public boolean existsById(Long aLong) {return false;}
      @Override public Iterable<UserInfo> findAll() {return null;}
      @Override public Iterable<UserInfo> findAllById(Iterable<Long> longs) {return null;}
      @Override public long count() {return 0;}
      @Override public void deleteById(Long aLong) {}
      @Override public void delete(UserInfo entity) {}
      @Override public void deleteAllById(Iterable<? extends Long> longs) {}
      @Override public void deleteAll(Iterable<? extends UserInfo> entities) {}
      @Override public void deleteAll() {}
    };
  }
}
