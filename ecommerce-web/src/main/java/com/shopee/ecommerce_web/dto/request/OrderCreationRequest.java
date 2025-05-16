package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationRequest {

    @NotNull(message = "User ID is mandatory")
    private String userId;

    @NotNull(message = "Order date is mandatory")
    private LocalDate orderDate;

    @NotNull(message = "Order status is mandatory")
    private String status;

    private String shippingMethod;

    private String paymentMethod;

    private Double amount;

}