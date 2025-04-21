package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Long cartItemId;  // ID của CartItem

    private Integer quantity;  // Số lượng sản phẩm trong giỏ hàng

    private UUID variantId;  // ID của ProductVariant (thay vì Product)

    private Long cartId;  // ID của giỏ hàng
}
