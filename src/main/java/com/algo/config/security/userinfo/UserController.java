package com.algo.config.security.userinfo;

import com.algo.config.security.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.security
 * @since : 07.11.23
 */
@Slf4j
@RestController
public class UserController {

  @GetMapping("/user")
  public CustomUser user(@CurrentUser CustomUser currentUser) {
    return currentUser;
  }

}
