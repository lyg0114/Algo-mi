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
@RequestMapping("/admin")
public class AdminController {

  @GetMapping("/main")
  public String adminAccess() {
    return "admin/main";
  }
}
