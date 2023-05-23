package com.cnc.outfit_ecommerce.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "outfit-ecommerce")
public class OutfitECommerceProperties {

  User user;

  @Data
  public static class User {
    private String jwtPrivateKey;
    private String jwtPublicKey;
  }
}
