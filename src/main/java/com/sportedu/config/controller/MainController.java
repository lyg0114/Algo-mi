package com.sportedu.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.controller
 * @since : 08.11.23
 */
@Controller
public class MainController {

  @GetMapping("/main-dashboard")
  public String mainDashBoard() {
    return "main-dashboard";
  }

  @GetMapping("/user-access")
  public String userAccess() {
    return "user-access";
  }

  @GetMapping("/admin-access")
  public String adminAccess() {
    return "admin-access";
  }

}
