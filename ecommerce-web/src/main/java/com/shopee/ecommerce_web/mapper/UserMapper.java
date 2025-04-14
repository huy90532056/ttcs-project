package com.shopee.ecommerce_web.mapper;

import com.shopee.ecommerce_web.dto.request.UserCreationRequest;
import com.shopee.ecommerce_web.dto.request.UserUpdateRequest;
import com.shopee.ecommerce_web.dto.response.UserResponse;
import com.shopee.ecommerce_web.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
