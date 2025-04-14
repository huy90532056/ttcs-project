package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemCreationRequest {

    private Long productId; // ID của sản phẩm

    private Integer quantity; // Số lượng sản phẩm

    private Double totalPrice; // Tổng giá cho sản phẩm

    private Long orderId; // ID của đơn hàng liên kết

}