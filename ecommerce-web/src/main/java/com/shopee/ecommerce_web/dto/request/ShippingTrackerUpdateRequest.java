package com.shopee.ecommerce_web.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingTrackerUpdateRequest {

    private String trackingNumber; // Số theo dõi vận chuyển (có thể thay đổi)

    private String shippingMethod; // Phương thức vận chuyển (có thể thay đổi)

    private String carrier; // Nhà vận chuyển (có thể thay đổi)

    private String status; // Trạng thái vận chuyển (có thể thay đổi)
}
