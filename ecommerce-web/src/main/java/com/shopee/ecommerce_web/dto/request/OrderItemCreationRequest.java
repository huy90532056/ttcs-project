package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemCreationRequest {

    @NotNull(message = "Variant ID is mandatory")
    private UUID variantId; // ID của ProductVariant (thay vì Product)

    @NotNull(message = "Quantity is mandatory")
    private Integer quantity; // Số lượng sản phẩm

    @NotNull(message = "Total price is mandatory")
    private Double totalPrice; // Tổng giá cho sản phẩm

    @NotNull(message = "Order ID is mandatory")
    private Long orderId; // ID của đơn hàng liên kết
}