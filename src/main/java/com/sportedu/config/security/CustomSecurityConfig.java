package com.sportedu.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config
 * @since : 07.11.23
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .formLogin(form -> form
            .loginPage("/login")
        )
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/resource/**")
            .hasAuthority("USER")
            .anyRequest().authenticated()
        )
    ;

    return http.build();
  }
}
