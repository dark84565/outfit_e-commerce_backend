package com.cnc.outfit_ecommerce.mapper;

import com.cnc.outfit_ecommerce.domain.User;
import com.cnc.outfit_ecommerce.dto.UserRequest;
import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
    componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    builder = @Builder)
public interface UserDtoMapper {
  User fromRequest(UserRequest userRequest);
}
