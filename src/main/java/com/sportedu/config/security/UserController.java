package com.sportedu.config.security;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.security
 * @since : 07.11.23
 */
@RestController
public class UserController {

  @GetMapping("/user")
  public CustomUser user(@CurrentUser CustomUser currentUser) {
    return currentUser;
  }

}
