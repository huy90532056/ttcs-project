package com.shopee.ecommerce_web.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long paymentId;  // ID của Payment

    private Long orderId;  // ID của đơn hàng liên kết với Payment

    private String paymentMethod; // Phương thức thanh toán (ví dụ: "Credit Card")

    private Double amount; // Số tiền thanh toán

    private LocalDate paymentDate; // Ngày thanh toán

}
