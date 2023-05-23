package com.cnc.outfit_ecommerce.dao;

import com.cnc.outfit_ecommerce.entity.KeyPairModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyPairModelRepository extends JpaRepository<KeyPairModel, String> {
  Optional<KeyPairModel> findByUserUuid(String userUuid);
}
