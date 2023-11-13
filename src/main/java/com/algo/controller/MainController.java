package com.algo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : iyeong-gyo
 * @package : com.algo.controller
 * @since : 13.11.23
 */
@Controller
public class MainController {

  @GetMapping("/")
  public String userAccess() {
    return "redirect:/customer/main-dashboard";
  }
}
