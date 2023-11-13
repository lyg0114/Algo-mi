package com.algo.config.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.security
 * @since : 07.11.23
 */
public class CustomUser {

  private final long id;

  private final String email;

  @JsonIgnore
  private final String password;

  @JsonCreator
  public CustomUser(long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  public long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

}
