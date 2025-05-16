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
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RabbitTemplate template;

    public OrderResponse createOrder(OrderCreationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Order order = Order.builder()
                .user(user)
                .orderDate(request.getOrderDate())
                .status(OrderStatus.valueOf(request.getStatus()))
                .shippingMethod(request.getShippingMethod())
                .paymentMethod(request.getPaymentMethod())
                .amount(request.getAmount())
                .build();

        order = orderRepository.save(order);

        // Gửi message RabbitMQ
        CustomMessage message = new CustomMessage();
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        message.setMessage(request.getUserId());
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, message);

        return mapToOrderResponse(order);
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return mapToOrderResponse(order);
    }

    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        order.setStatus(OrderStatus.valueOf(request.getStatus()));
        order = orderRepository.save(order);

        return mapToOrderResponse(order);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        orderRepository.delete(order);
    }

    public List<OrderResponse> getOrdersByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return orderRepository.findByUser(user).stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus().name())
                .shippingMethod(order.getShippingMethod())
                .paymentMethod(order.getPaymentMethod())
                .amount(order.getAmount())

                // Sửa ở đây: dùng Optional để tránh null
                .items(Optional.ofNullable(order.getItems())
                        .orElse(Collections.emptyList())  // nếu null thì trả về list rỗng
                        .stream()
                        .map(item -> OrderItemResponse.builder()
                                .orderItemId(item.getOrderItemId())
                                .variantId(item.getProductVariant().getVariantId())
                                .quantity(item.getQuantity())
                                .totalPrice(item.getTotalPrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

}
