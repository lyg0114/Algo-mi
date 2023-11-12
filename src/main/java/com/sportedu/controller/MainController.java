package com.sportedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.controller
 * @since : 08.11.23
 */
@Controller
@RequestMapping("/customer")
public class MainController {

  @GetMapping("/main-dashboard")
  public String mainDashBoard() {
    return "customer/main-dashboard";
  }

  @GetMapping("/user-access")
  public String userAccess() {
    return "customer/user-access";
  }

  @GetMapping("/admin-access")
  public String adminAccess() {
    return "admin/admin-access";
  }
}
