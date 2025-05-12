package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreationRequest {
    @NotBlank(message = "PRODUCTNAME_EMPTY")
    private String productName;
    @NotBlank(message = "sku is mandatory")
    private String sku;
    private Double price;
    private String description;
    private MultipartFile productImageFile;
    private Double productWeight;
    private Boolean published;

}