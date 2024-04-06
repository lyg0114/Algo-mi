package com.algo.alert.application;

import com.algo.auth.domain.CheckEmail;
import com.algo.exception.custom.SignUpFailException;
import com.algo.recovery.application.RecoveryService;
import com.algo.recovery.dto.RecoveryContents;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.algo.email.application
 * @since : 12.03.24
 */
@Profile({"local", "prod", "dev", "localprod"})
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

  @Value("${spring.mail.username}")
  private String officialEmail;
  @Value("${cors.allowed-origins}")
  private String host;

  private final JavaMailSender emailSender;
  private final RecoveryService recoveryService;
  private final SimpleMailMessage message;

  @Override
  @Scheduled(cron = "${cron.rule}", zone = "${time.zone}")
  public void sendSimpleMessage() {
    List<RecoveryContents> contents = recoveryService.createContents();
    for (RecoveryContents content : contents) {
      message.setFrom(officialEmail);
      message.setTo(content.getEmail());
      message.setSubject(recoveryService.createTitle());
      message.setText(content.getContents());
      emailSender.send(message);
    }
  }

  @Override
  public void sendSignUpEamil(CheckEmail checkEmail) {
    try {
      String confirmUrl = host + "/check-email?token=" + checkEmail.getCheckId();
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(officialEmail);
      String email = checkEmail.getUserInfo().getEmail();
      message.setTo(email);
      message.setSubject("[AGO-MI 회원가입 인증]");
      String htmlContent =
          "[AGO-MI 회원가입 인증] \n" +
              "아래의 링크를 클릭하여 본인 인증을 완료하여 주세요.\n" +
              confirmUrl + "\n" +
              "이 링크는 15분 동안 유효합니다. \n" +
              "감사합니다.\n";
      message.setText(htmlContent);
      emailSender.send(message);
    } catch (MailException ex) {
      log.error("error : Email transmission failed.");
      log.error(ex.getMessage());
      throw new SignUpFailException("메일 전송에 실패하였습니다. 관리자에게 문의하여 주세요");
    }
  }
}
