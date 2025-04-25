package com.shopee.ecommerce_web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class OrderItemResponse {
    private Long orderItemId;
    private UUID variantId;
    private Integer quantity;
    private Double totalPrice;
}