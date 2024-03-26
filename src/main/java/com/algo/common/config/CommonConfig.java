package com.algo.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

/**
 * @author : iyeong-gyo
 * @package : com.algo.config
 * @since : 20.11.23
 */
@Configuration
public class CommonConfig {

  @Autowired
  private RestTemplateBuilder builder;

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public RestTemplate restTemplate() {
    return builder.build();
  }

  @Bean
  public SimpleMailMessage simpleMailMessage(){
    return new SimpleMailMessage();
  }
}
