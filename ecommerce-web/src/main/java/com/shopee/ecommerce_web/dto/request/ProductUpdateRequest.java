package com.shopee.ecommerce_web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    private String productName;
    private Double price;
    private String description;
    private String productImage;
    private Double productWeight;
    private Boolean published;

}
