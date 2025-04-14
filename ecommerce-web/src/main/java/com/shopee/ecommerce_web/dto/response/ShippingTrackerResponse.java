package com.shopee.ecommerce_web.dto.response;

import com.shopee.ecommerce_web.entity.ShippingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShippingTrackerResponse {

    private Long trackingId; // ID theo dõi vận chuyển

    private Long orderId; // ID của đơn hàng

    private String trackingNumber; // Số theo dõi vận chuyển

    private String shippingMethod; // Phương thức vận chuyển

    private String carrier; // Nhà vận chuyển

    private ShippingStatus status; // Trạng thái vận chuyển
}
