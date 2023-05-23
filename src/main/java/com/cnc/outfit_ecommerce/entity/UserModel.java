package com.cnc.outfit_ecommerce.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "user")
public class UserModel extends BaseModel {

  private String username;

  private String email;

  private String password;

  private String uuid;
}
