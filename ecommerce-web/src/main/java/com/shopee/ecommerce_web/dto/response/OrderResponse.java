package com.shopee.ecommerce_web.dto.response;

import com.shopee.ecommerce_web.entity.OrderItem;
import com.shopee.ecommerce_web.entity.OrderStatus;
import com.shopee.ecommerce_web.entity.Payment;
import com.shopee.ecommerce_web.entity.ShippingTracker;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private Long orderId; // ID đơn hàng

    private String userId; // ID của khách hàng

    private LocalDate orderDate; // Ngày đặt hàng

    private String status; // Trạng thái đơn hàng
    private String shippingMethod;

    private String paymentMethod;

    private Double amount;
    private String shipperId;

    private List<OrderItemResponse> items; // Danh sách sản phẩm trong đơn hàng
}
