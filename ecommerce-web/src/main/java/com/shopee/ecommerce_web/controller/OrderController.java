package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.OrderCreationRequest;
import com.shopee.ecommerce_web.dto.request.OrderUpdateRequest;
import com.shopee.ecommerce_web.dto.response.OrderResponse;
import com.shopee.ecommerce_web.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    // Create a new Order
    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreationRequest request) {
        OrderResponse orderResponse = orderService.createOrder(request);
        return ApiResponse.<OrderResponse>builder()
                .result(orderResponse)
                .build();
    }

    // Get all Orders
    @GetMapping
    public ApiResponse<List<OrderResponse>> getOrders() {
        List<OrderResponse> orders = orderService.getOrders();
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orders)
                .build();
    }

    // Get Order by ID
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.getOrder(orderId);
        return ApiResponse.<OrderResponse>builder()
                .result(orderResponse)
                .build();
    }

    // Update Order status
    @PutMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(@PathVariable Long orderId, @RequestBody OrderUpdateRequest request) {
        OrderResponse orderResponse = orderService.updateOrder(orderId, request);
        return ApiResponse.<OrderResponse>builder()
                .result(orderResponse)
                .build();
    }

    // Delete Order
    @DeleteMapping("/{orderId}")
    public ApiResponse<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ApiResponse.<String>builder()
                .result("Order has been deleted")
                .build();
    }
}
