package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantResponse {

    private UUID variantId;
    private String variantName;
    private String variantValue;
    private Double price;
    private Integer stockQuantity;
    private Long productId;
}
