package com.cnc.outfit_ecommerce.config;

import com.cnc.outfit_ecommerce.dao.KeyPairModelRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(
      HttpSecurity http, KeyPairModelRepository keyPairModelRepository) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/api/login/login", "/api/login/register")
        .permitAll()
        .anyRequest()
        .permitAll();
    // TODO: add Authentication when using spring security
    // TODO: add api permission

    //        .anyRequest()
    //        .authenticated()
    //        .and()
    //        .addFilterAfter(new JwtAuthenticationFilter(keyPairModelRepository),
    // BasicAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    int cpuCost = (int) Math.pow(2, 14);
    int memoryCost = 8;
    int parallelization = 1;
    int keyLength = 32;
    int saltLength = 64;
    return new SCryptPasswordEncoder(cpuCost, memoryCost, parallelization, keyLength, saltLength);
  }
}
