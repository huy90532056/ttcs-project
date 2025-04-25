package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String categoryId;
    private String categoryName;
    private String categoryDescription;
    private String categoryIcon;
    private String categoryImagePath;
    private Boolean active;
}