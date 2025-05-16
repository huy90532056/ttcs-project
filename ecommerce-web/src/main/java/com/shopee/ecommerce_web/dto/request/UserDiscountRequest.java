package com.shopee.ecommerce_web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDiscountRequest {
    private String userId;
    private Long discountId;
}
