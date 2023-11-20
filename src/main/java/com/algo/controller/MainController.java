package com.algo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : iyeong-gyo
 * @package : com.algo.controller
 * @since : 13.11.23
 */
@Slf4j
@Controller
public class MainController {

  @GetMapping("/")
  public String userAccess() {
    return "redirect:/customer/main-dashboard";
  }
}
