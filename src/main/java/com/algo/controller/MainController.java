package com.algo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.controller
 * @since : 08.11.23
 */
@Controller
public class MainController {

  @GetMapping("/main-dashboard")
  public String mainDashBoard() {
    return "main-dashboard";
  }

}
