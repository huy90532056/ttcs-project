package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemCreationRequest {

    @NotNull(message = "Variant ID is mandatory")
    private UUID variantId;

    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;

    @NotNull(message = "Total price is mandatory")
    private Double totalPrice;

    @NotNull(message = "Order ID is mandatory")
    private Long orderId;
}