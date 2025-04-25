package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.configuration.MQConfig;
import com.shopee.ecommerce_web.dto.custom.CustomMessage;
import com.shopee.ecommerce_web.dto.request.OrderCreationRequest;
import com.shopee.ecommerce_web.dto.request.OrderUpdateRequest;
import com.shopee.ecommerce_web.dto.response.OrderItemResponse;
import com.shopee.ecommerce_web.dto.response.OrderResponse;
import com.shopee.ecommerce_web.entity.Order;
import com.shopee.ecommerce_web.entity.OrderStatus;
import com.shopee.ecommerce_web.entity.User;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.OrderRepository;
import com.shopee.ecommerce_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    private RabbitTemplate template;

    // Tạo một đơn hàng mới
    public OrderResponse createOrder(OrderCreationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .user(user)
                .orderDate(request.getOrderDate())
                .status(OrderStatus.valueOf(request.getStatus()))
                .build();

        order = orderRepository.save(order);

        // Gửi message qua MQ
        CustomMessage message = new CustomMessage();
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        message.setMessage(request.getUserId());
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, message);

        // Trả về OrderResponse
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .items(order.getItems())  // Trả về List<OrderItem> trực tiếp
                .build();
    }

    // Lấy tất cả các đơn hàng
    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getOrderId())
                        .userId(order.getUser().getId()) // Lấy userId từ đối tượng User
                        .orderDate(order.getOrderDate())
                        .status(order.getStatus())
                        .items(order.getItems()) // Trả về List<OrderItem> trực tiếp
                        .build())
                .collect(Collectors.toList());
    }

    // Lấy đơn hàng theo ID
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getId())  // Lấy userId từ đối tượng User
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .items(order.getItems()) // Trả về List<OrderItem> trực tiếp
                .build();
    }

    // Cập nhật đơn hàng
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        order.setStatus(OrderStatus.valueOf(request.getStatus()));
        order = orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getId())  // Lấy userId từ đối tượng User
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .items(order.getItems()) // Trả về List<OrderItem> trực tiếp
                .build();
    }

    // Xóa đơn hàng
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        orderRepository.delete(order);
    }

    // Lấy đơn hàng theo ID của User
    public List<OrderResponse> getOrdersByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getOrderId())
                        .userId(order.getUser().getId())
                        .orderDate(order.getOrderDate())
                        .status(order.getStatus())
                        .items(order.getItems()) // Trả về List<OrderItem> trực tiếp
                        .build())
                .collect(Collectors.toList());
    }
}
