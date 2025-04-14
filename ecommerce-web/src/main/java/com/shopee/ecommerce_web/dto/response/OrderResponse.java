package com.shopee.ecommerce_web.dto.response;

import com.shopee.ecommerce_web.entity.OrderItem;
import com.shopee.ecommerce_web.entity.OrderStatus;
import com.shopee.ecommerce_web.entity.Payment;
import com.shopee.ecommerce_web.entity.ShippingTracker;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private Long orderId; // ID đơn hàng

    private String userId; // ID của khách hàng (User)

    private LocalDate orderDate; // Ngày đặt hàng

    private OrderStatus status; // Trạng thái đơn hàng

    private List<OrderItem> items; // Danh sách các sản phẩm trong đơn hàng

    private Payment payment; // Thông tin thanh toán

    private ShippingTracker shipping; // Thông tin giao hàng

}
