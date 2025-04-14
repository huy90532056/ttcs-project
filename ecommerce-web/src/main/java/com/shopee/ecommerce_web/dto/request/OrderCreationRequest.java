package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderCreationRequest {

    @NotNull(message = "User ID is mandatory")
    private String userId; // ID của khách hàng (User)

    @NotNull(message = "Order date is mandatory")
    private LocalDate orderDate; // Ngày đặt hàng

    @NotNull(message = "Order status is mandatory")
    private String status; // Trạng thái đơn hàng (ví dụ: "PENDING", "COMPLETED", "CANCELLED")

}