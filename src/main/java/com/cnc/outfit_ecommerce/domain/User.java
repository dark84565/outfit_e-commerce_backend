package com.cnc.outfit_ecommerce.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private Long id;

  private LocalDateTime createdTime;

  private LocalDateTime updatedTime;

  private String username;

  private String email;

  private String password;

  private String uuid;
}
