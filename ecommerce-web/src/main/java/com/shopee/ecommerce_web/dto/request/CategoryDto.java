package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotBlank(message = "Category name is required")
    private String categoryName;

    private String categoryDescription;
    private String categoryIcon;
    private String categoryImagePath;
    private Boolean active;
}