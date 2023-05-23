package com.cnc.outfit_ecommerce.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

  @NotBlank
  @Size(max = 32)
  private String username;

  @Email
  @Size(max = 32)
  private String email;

  @NotBlank
  @Size(max = 32)
  private String password;
}
