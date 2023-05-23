package com.cnc.outfit_ecommerce.mapper;

import com.cnc.outfit_ecommerce.domain.Post;
import com.cnc.outfit_ecommerce.dto.PostRequest;
import com.cnc.outfit_ecommerce.dto.PostResponse;
import java.util.List;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    builder = @Builder)
public interface PostDtoMapper {
  Post fromRequest(PostRequest postRequest, List<String> photoPaths);

  PostResponse toResponse(Post source);

  List<PostResponse> toResponse(List<Post> post);
}
