package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Long cartItemId;  // ID của CartItem

    private Integer quantity;  // Số lượng sản phẩm trong giỏ hàng

    private Long productId;  // ID của sản phẩm

    private Long cartId;  // ID của giỏ hàng
}
