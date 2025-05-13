package com.shopee.ecommerce_web.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCreationRequest {
    private String userId;
    private MultipartFile imageFile;
}
