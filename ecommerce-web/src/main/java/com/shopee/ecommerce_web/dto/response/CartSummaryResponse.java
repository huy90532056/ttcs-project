package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartSummaryResponse {
    private int totalItems;
    private double totalPrice;
}
