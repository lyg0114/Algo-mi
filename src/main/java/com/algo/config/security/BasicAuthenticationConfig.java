package com.algo.config.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config
 * @since : 07.11.23
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(name = "algomi.security.enable", havingValue = "true")
public class BasicAuthenticationConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    http
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/main-dashboard")
        )
    ;
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(antMatcher("/login")).permitAll()
            .anyRequest()
            .authenticated()
        ).csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
