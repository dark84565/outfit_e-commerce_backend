package com.cnc.outfit_ecommerce.mapper;

import com.cnc.outfit_ecommerce.domain.Post;
import com.cnc.outfit_ecommerce.entity.PhotoModel;
import com.cnc.outfit_ecommerce.entity.PostModel;
import com.cnc.outfit_ecommerce.entity.TagModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    builder = @Builder)
public interface PostModelMapper {
  @Mapping(
      target = "photos",
      source = "photoPaths",
      qualifiedByName = "photoPathConvertToPhotoModel")
  @Mapping(target = "tags", source = "tags", qualifiedByName = "tagStringConvertToTagModel")
  PostModel toModel(Post post);

  @Named("photoPathConvertToPhotoModel")
  default Set<PhotoModel> photoPathConvertToPhotoModel(List<String> paths) {
    Set<PhotoModel> photoModels = new HashSet<>();
    paths.forEach(p -> photoModels.add(PhotoModel.builder().path(p).build()));
    return photoModels;
  }

  @Named("tagStringConvertToTagModel")
  default Set<TagModel> tagStringConvertToTagModel(List<String> tags) {
    Set<TagModel> tagModels = new HashSet<>();
    tags.forEach(tag -> tagModels.add(TagModel.builder().tag(tag).build()));
    return tagModels;
  }

  @Mapping(
      target = "photoPaths",
      source = "photos",
      qualifiedByName = "photoModelConvertToPhotoPath")
  @Mapping(target = "tags", source = "tags", qualifiedByName = "tagModelConvertToTagString")
  Post fromModel(PostModel source);

  @Named("photoModelConvertToPhotoPath")
  default List<String> photoModelConvertToPhotoPath(Set<PhotoModel> photoModels) {
    List<String> photoPath = new ArrayList<>();
    photoModels.forEach(p -> photoPath.add(p.getPath()));
    return photoPath;
  }

  @Named("tagModelConvertToTagString")
  default List<String> tagModelConvertToTagString(Set<TagModel> tagModels) {
    List<String> tags = new ArrayList<>();
    tagModels.forEach(t -> tags.add(t.getTag()));
    return tags;
  }

  List<Post> fromModel(List<PostModel> source);
}
