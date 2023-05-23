package com.cnc.outfit_ecommerce.dao;

import com.cnc.outfit_ecommerce.entity.PostModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostModelRepository extends JpaRepository<PostModel, String> {
  List<PostModel> findByUserUuid(String userUuid);
}
