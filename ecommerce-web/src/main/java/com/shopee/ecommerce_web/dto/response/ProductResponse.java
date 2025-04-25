package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String productName;
    private String sku;
    private Double price;
    private String description;
    private String productImage;
    private Double productWeight;
    private Boolean published;

    private List<CategoryResponse> categories;
    private List<TagResponse> tags;
}
