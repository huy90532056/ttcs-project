package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantRequest {

    @NotBlank(message = "Variant name is required")
    private String variantName;

    @NotBlank(message = "Variant value is required")
    private String variantValue;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Stock quantity is required")
    private Integer stockQuantity;

    @NotNull(message = "Product ID is required")
    private Long productId;
}
