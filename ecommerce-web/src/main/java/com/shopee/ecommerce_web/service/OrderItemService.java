package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.OrderItemCreationRequest;
import com.shopee.ecommerce_web.dto.request.OrderItemUpdateRequest;
import com.shopee.ecommerce_web.dto.response.OrderItemResponse;
import com.shopee.ecommerce_web.entity.Order;
import com.shopee.ecommerce_web.entity.OrderItem;
import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.OrderItemRepository;
import com.shopee.ecommerce_web.repository.OrderRepository;
import com.shopee.ecommerce_web.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // Tạo mới OrderItem
    public OrderItemResponse createOrderItem(OrderItemCreationRequest request) {
        // Kiểm tra nếu Order và Product tồn tại
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Tạo mới OrderItem
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .totalPrice(request.getTotalPrice())
                .build();

        // Lưu OrderItem vào cơ sở dữ liệu
        orderItem = orderItemRepository.save(orderItem);

        // Trả về OrderItemResponse
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProduct().getProductId())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    // Lấy tất cả OrderItems
    public List<OrderItemResponse> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(orderItem -> OrderItemResponse.builder()
                        .orderItemId(orderItem.getOrderItemId())
                        .productId(orderItem.getProduct().getProductId())
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
                .productId(orderItem.getProduct().getProductId())
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
                .productId(orderItem.getProduct().getProductId())
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
