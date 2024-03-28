package com.algo.alert.application;

import com.algo.auth.domain.EmailCheck;
import com.algo.recovery.application.RecoveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.algo.email.application
 * @since : 12.03.24
 */
@Profile({"local", "prod", "dev" ,"localprod"})
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

  @Value("${spring.mail.username}")
  private String from;
  @Value("${spring.mail.username}")
  private String to;
  @Value("${cors.allowed-origins}")
  private String host;

  private final JavaMailSender emailSender;
  private final RecoveryService recoveryService;
  private final SimpleMailMessage message;

  @Override
  @Scheduled(cron = "${cron.rule}", zone = "${time.zone}")
  public void sendSimpleMessage() {
    String title = recoveryService.createTitle();
    String text = recoveryService.createText();
    message.setFrom(from);
    message.setTo(to); //TODO : 설정파일을 읽어 가져오는 to 정보를 DB에서 불러와서 사용자별로 전송되도록 해야함.
    message.setSubject(title);
    message.setText(text);
    emailSender.send(message);
  }

  @Override
  public void sendSignUpEamil(EmailCheck emailCheck) {
    String confirmUrl = host + "/check-email?token=" + emailCheck.getCheckId();
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    String email = emailCheck.getUserInfo().getEmail();
    message.setTo(email);
    message.setSubject("[AGO-MI 회원가입 인증]");

    //TODO : 이메일 내용 CSS 개선 필요
    String htmlContent = "<html><body>" +
        "<p>아래의 링크를 클릭하여 인증을 완료하여 주세요.</p>" +
        "<p><a href=\"" + confirmUrl + "\">인증 링크</a></p>" +
        "<p>이 링크는 15분 동안 유효합니다. 만약 인증을 완료하지 못하셨다면 다시 시도해주세요.</p>" +
        "<p>감사합니다.</p>" +
        "</body></html>";

    message.setText(htmlContent);
    emailSender.send(message);
  }
}
