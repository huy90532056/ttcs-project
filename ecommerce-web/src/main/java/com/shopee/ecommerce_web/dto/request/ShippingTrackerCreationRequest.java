package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingTrackerCreationRequest {

    @NotNull(message = "Order ID is mandatory")
    private Long orderId; // ID của đơn hàng

    private String trackingNumber; // Số theo dõi vận chuyển

    private String shippingMethod; // Phương thức vận chuyển

    private String carrier; // Nhà vận chuyển

    @NotNull(message = "Shipping status is mandatory")
    private String status; // Trạng thái vận chuyển (ví dụ: "In Transit", "Delivered")
}
