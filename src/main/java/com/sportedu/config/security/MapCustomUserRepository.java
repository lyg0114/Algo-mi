package com.sportedu.config.security;

import lombok.RequiredArgsConstructor;
import java.util.Map;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.security
 * @since : 07.11.23
 */
@RequiredArgsConstructor
public class MapCustomUserRepository implements CustomUserRepository {

  private final Map<String, CustomUser> emailToCustomUser;

  @Override
  public CustomUser findCustomUserByEmail(String email) {
    return this.emailToCustomUser.get(email);
  }
}
