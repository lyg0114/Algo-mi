package com.algo.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author : iyeong-gyo
 * @package : com.algo.controller.rest
 * @since : 19.12.23
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample")
public class SlackRestController {

  @Value(value = "${slack.webhook.url}")
  private String slackWebHookUrl;
  private final RestTemplate restTemplate;

  @GetMapping("/slack")
  public String postInquiry() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String messageJson = "{\"text\":\"" + "SUCCESS" + "\"}";
    HttpEntity<String> requestEntity = new HttpEntity<>(messageJson, headers);

    ResponseEntity<String> responseEntity
        = restTemplate.postForEntity(slackWebHookUrl, requestEntity, String.class);

    int statusCode = responseEntity.getStatusCodeValue();
    System.out.println("Slack에 메시지 전송 결과: " + statusCode);

    return "success";
  }
}

