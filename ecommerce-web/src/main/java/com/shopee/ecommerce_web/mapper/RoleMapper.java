package com.shopee.ecommerce_web.mapper;

import com.shopee.ecommerce_web.dto.request.RoleRequest;
import com.shopee.ecommerce_web.dto.response.RoleResponse;
import com.shopee.ecommerce_web.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}