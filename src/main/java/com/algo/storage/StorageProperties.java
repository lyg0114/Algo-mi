package com.algo.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

  public static String LOCATION = "src/main/resources/static/images";

  public String getLocation() {
    return LOCATION;
  }

  public void setLocation(String location) {
    this.LOCATION = location;
  }
}
