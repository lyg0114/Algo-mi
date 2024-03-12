package com.algo.email.ui;

import com.algo.email.application.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : iyeong-gyo
 * @package : com.algo.email.ui
 * @since : 12.03.24
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/email")
public class EmailController {

  private final EmailService emailService;

  @ResponseBody
  @RequestMapping(value = "/test", method = RequestMethod.POST)
  public String email() {

    emailService.sendSimpleMessage(
        "whdnseowkd@gmail.com",
        "TEST SUBJECT",
        "Contents"
    );

    return "SUCCESS";
  }
}
