package com.cnc.outfit_ecommerce.dao;

import com.cnc.outfit_ecommerce.entity.UserModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserModelRepository extends JpaRepository<UserModel, String> {
  Optional<UserModel> findByEmail(String email);
}
