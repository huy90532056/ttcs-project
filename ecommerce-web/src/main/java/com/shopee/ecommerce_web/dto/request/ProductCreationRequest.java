package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreationRequest {
    @NotBlank(message = "PRODUCTNAME_EMPTY")
    @Size(min = 2, max = 30, message = "PRODUCTNAME_INVALID")
    private String productName;
    @NotBlank(message = "sku is mandatory")
    private String sku;
    private Double price;
    private String description;
    private String productImage;
    private Double productWeight;
    private Boolean published;

}