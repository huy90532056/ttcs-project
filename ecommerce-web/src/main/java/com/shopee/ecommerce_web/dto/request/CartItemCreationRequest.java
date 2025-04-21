package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CartItemCreationRequest {

    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;  // Số lượng sản phẩm trong giỏ hàng

    @NotNull(message = "Variant ID is mandatory")
    private UUID variantId;  // ID của ProductVariant (thay vì Product)

    @NotNull(message = "Cart ID is mandatory")
    private Long cartId;  // ID của giỏ hàng
}