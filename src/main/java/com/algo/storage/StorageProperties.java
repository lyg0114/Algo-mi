package com.algo.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

  // TODO : profile 옵션에따라 다르게 동작하도록 처리 해야함.
  // 실제물리환경에서
//  public static String LOCATION = "src/main/resources/static/images";

  // 도커환경에서
  public static String LOCATION = "/home";

  public String getLocation() {
    return LOCATION;
  }

  public void setLocation(String location) {
    this.LOCATION = location;
  }
}
