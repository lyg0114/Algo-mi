package com.algo.alert.application;

import com.algo.auth.domain.EmailCheck;
import com.algo.recovery.application.RecoveryService;
import java.util.UUID;
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
  @Value("${cors.allowed-origins}")
  private String host;

  private final JavaMailSender emailSender;
  private final RecoveryService recoveryService;
  private final SimpleMailMessage message;

  @Scheduled(cron = "${cron.rule}", zone = "${time.zone}")
  public void sendSimpleMessage() {
    String title = recoveryService.createTitle();
    String text = recoveryService.createText();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(title);
    message.setText(text);
    emailSender.send(message);
  }

  public void sendSignUpEamil(EmailCheck emailCheck) {
    String token = UUID.randomUUID().toString();
    String confirmUrl = host + "api/rest/auth/check-email/" + emailCheck.getCheckId();

    StringBuffer sb = new StringBuffer();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject("[AGO-MI 회원가입 인증]");

    sb.append("아래의 링크를 클릭하여 인증을 완료하여 주세요.\n");
    sb.append(confirmUrl);
    message.setText(sb.toString());
    emailSender.send(message);
  }
}
