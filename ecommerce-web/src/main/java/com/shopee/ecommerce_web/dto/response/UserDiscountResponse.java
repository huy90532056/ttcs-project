package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDiscountResponse {
    private Long id;
    private String userId;
    private Long discountId;
    private Boolean isUsed;
    private Date usedAt;
}
