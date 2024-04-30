package com.algo.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

  // 실제물리환경에서
//  public static String LOCATION = "src/main/resources/static/images";

  // 도커환경에서
  public static String LOCATION = "/path/to/upload/directory";

  public String getLocation() {
    return LOCATION;
  }

  public void setLocation(String location) {
    this.LOCATION = location;
  }
}
