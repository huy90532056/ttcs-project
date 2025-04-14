package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long cartId;
    private String userId;
    private List<CartItemResponse> cartItems; // Thêm trường này để trả về danh sách CartItem
}
