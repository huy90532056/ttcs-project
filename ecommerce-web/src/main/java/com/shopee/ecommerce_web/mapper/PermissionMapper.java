package com.shopee.ecommerce_web.mapper;

import com.shopee.ecommerce_web.dto.request.PermissionRequest;
import com.shopee.ecommerce_web.dto.response.PermissionResponse;
import com.shopee.ecommerce_web.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}