package com.sportedu.config.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.controller
 * @since : 08.11.23
 */
@Slf4j
@Controller
public class LoginController {

  @GetMapping("/login")
  String login() {
    return "custom-login";
  }
}
