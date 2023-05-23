package com.cnc.outfit_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableJpaAuditing
@SpringBootApplication
public class OutfitECommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OutfitECommerceApplication.class, args);
  }
}
