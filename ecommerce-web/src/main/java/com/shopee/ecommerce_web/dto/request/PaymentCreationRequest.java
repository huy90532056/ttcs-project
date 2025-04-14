package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreationRequest {

    @NotNull(message = "Order ID is mandatory")
    private Long orderId;  // ID của đơn hàng liên kết với Payment

    @NotNull(message = "Payment method is mandatory")
    private String paymentMethod; // Phương thức thanh toán (ví dụ: "Credit Card")

    @NotNull(message = "Amount is mandatory")
    private Double amount; // Số tiền thanh toán

    @NotNull(message = "Payment date is mandatory")
    private LocalDate paymentDate; // Ngày thanh toán

}
