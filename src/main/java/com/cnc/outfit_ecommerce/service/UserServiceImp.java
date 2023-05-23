package com.cnc.outfit_ecommerce.service;

import com.cnc.outfit_ecommerce.dao.PostModelRepository;
import com.cnc.outfit_ecommerce.domain.Post;
import com.cnc.outfit_ecommerce.entity.PostModel;
import com.cnc.outfit_ecommerce.mapper.PostModelMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {
  private final PostModelRepository postModelRepository;
  private final PostModelMapper postModelMapper;

  public void savePost(Post post) {
    postModelRepository.save(postModelMapper.toModel(post));
  }

  public List<Post> getPost(String userUuid) {
    List<PostModel> postModels = postModelRepository.findByUserUuid(userUuid);

    // TODO: return List<Post>
    return postModelMapper.fromModel(postModels);
  }
}
