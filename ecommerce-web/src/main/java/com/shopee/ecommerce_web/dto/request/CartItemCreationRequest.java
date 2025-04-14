package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemCreationRequest {

    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;  // Số lượng sản phẩm trong giỏ hàng

    @NotNull(message = "Product ID is mandatory")
    private Long productId;  // ID của sản phẩm

    @NotNull(message = "Cart ID is mandatory")
    private Long cartId;  // ID của giỏ hàng
}
