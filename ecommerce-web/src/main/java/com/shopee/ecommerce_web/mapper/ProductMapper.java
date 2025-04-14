package com.shopee.ecommerce_web.mapper;

import com.shopee.ecommerce_web.dto.request.ProductCreationRequest;
import com.shopee.ecommerce_web.dto.request.ProductUpdateRequest;
import com.shopee.ecommerce_web.dto.response.ProductResponse;
import com.shopee.ecommerce_web.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductCreationRequest request);
    ProductResponse toProductResponse(Product product);
    void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);
}
