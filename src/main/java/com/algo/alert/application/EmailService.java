package com.algo.alert.application;

import com.algo.recovery.application.RecoveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.algo.email.application
 * @since : 12.03.24
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

  @Value("${spring.mail.username}")
  private String from;
  @Value("${spring.mail.username}")
  private String to;

  private final JavaMailSender emailSender;
  private final RecoveryService recoveryService;

  @Scheduled(cron = "${cron.rule}", zone = "${time.zone}")
  public void sendSimpleMessage() {
    String text = recoveryService.createText();
    String title = recoveryService.createTitle();
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(title);
    message.setText(text);
    emailSender.send(message);
  }
}
