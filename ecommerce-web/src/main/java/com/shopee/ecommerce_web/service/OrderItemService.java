package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.OrderItemCreationRequest;
import com.shopee.ecommerce_web.dto.request.OrderItemUpdateRequest;
import com.shopee.ecommerce_web.dto.response.OrderItemResponse;
import com.shopee.ecommerce_web.entity.Order;
import com.shopee.ecommerce_web.entity.OrderItem;
import com.shopee.ecommerce_web.entity.ProductVariant;  // Chuyển từ Product sang ProductVariant
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.OrderItemRepository;
import com.shopee.ecommerce_web.repository.OrderRepository;
import com.shopee.ecommerce_web.repository.ProductVariantRepository;  // Repository của ProductVariant
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductVariantRepository productVariantRepository;  // Sử dụng ProductVariantRepository

    // Tạo mới OrderItem
    public OrderItemResponse createOrderItem(OrderItemCreationRequest request) {
        // Kiểm tra nếu Order và ProductVariant tồn tại
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        ProductVariant productVariant = productVariantRepository.findById(request.getVariantId())  // Lấy theo ProductVariant
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));  // Thay đổi lỗi thành PRODUCT_VARIANT_NOT_FOUND

        // Tạo mới OrderItem
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .productVariant(productVariant)  // Gán ProductVariant vào OrderItem
                .quantity(request.getQuantity())
                .totalPrice(request.getTotalPrice())
                .build();

        // Lưu OrderItem vào cơ sở dữ liệu
        orderItem = orderItemRepository.save(orderItem);

        // Trả về OrderItemResponse
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .variantId(orderItem.getProductVariant().getVariantId())  // Sử dụng productVariantId
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    // Lấy tất cả OrderItems
    public List<OrderItemResponse> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(orderItem -> OrderItemResponse.builder()
                        .orderItemId(orderItem.getOrderItemId())
                        .variantId(orderItem.getProductVariant().getVariantId())  // Sử dụng productVariantId
                        .quantity(orderItem.getQuantity())
                        .totalPrice(orderItem.getTotalPrice())
                        .build())
                .collect(Collectors.toList());
    }

    // Lấy OrderItem theo ID
    public OrderItemResponse getOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND));

        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .variantId(orderItem.getProductVariant().getVariantId())  // Sử dụng productVariantId
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    // Cập nhật OrderItem
    public OrderItemResponse updateOrderItem(Long orderItemId, OrderItemUpdateRequest request) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND));

        // Cập nhật các trường thông tin của OrderItem
        if (request.getQuantity() != null) {
            orderItem.setQuantity(request.getQuantity());
        }
        if (request.getTotalPrice() != null) {
            orderItem.setTotalPrice(request.getTotalPrice());
        }

        // Lưu lại OrderItem đã cập nhật
        orderItem = orderItemRepository.save(orderItem);

        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .variantId(orderItem.getProductVariant().getVariantId())  // Sử dụng productVariantId
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    // Xóa OrderItem
    public void deleteOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ITEM_NOT_FOUND));

        orderItemRepository.delete(orderItem);
    }
}
