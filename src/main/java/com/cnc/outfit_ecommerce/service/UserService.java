package com.cnc.outfit_ecommerce.service;

import com.cnc.outfit_ecommerce.domain.Post;
import java.util.List;

public interface UserService {
  void savePost(Post post);

  List<Post> getPost(String userUuid);
}
