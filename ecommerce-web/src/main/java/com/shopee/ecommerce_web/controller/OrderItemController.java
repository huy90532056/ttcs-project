package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.OrderItemCreationRequest;
import com.shopee.ecommerce_web.dto.request.OrderItemUpdateRequest;
import com.shopee.ecommerce_web.dto.response.OrderItemResponse;
import com.shopee.ecommerce_web.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    // Tạo mới OrderItem
    @PostMapping
    public ApiResponse<OrderItemResponse> createOrderItem(@RequestBody OrderItemCreationRequest request) {
        OrderItemResponse response = orderItemService.createOrderItem(request);
        return ApiResponse.<OrderItemResponse>builder()
                .result(response)
                .build();
    }

    // Lấy tất cả OrderItems
    @GetMapping
    public ApiResponse<List<OrderItemResponse>> getAllOrderItems() {
        List<OrderItemResponse> responses = orderItemService.getAllOrderItems();
        return ApiResponse.<List<OrderItemResponse>>builder()
                .result(responses)
                .build();
    }

    // Lấy OrderItem theo ID
    @GetMapping("/{orderItemId}")
    public ApiResponse<OrderItemResponse> getOrderItem(@PathVariable Long orderItemId) {
        OrderItemResponse response = orderItemService.getOrderItem(orderItemId);
        return ApiResponse.<OrderItemResponse>builder()
                .result(response)
                .build();
    }

    // Cập nhật OrderItem
    @PutMapping("/{orderItemId}")
    public ApiResponse<OrderItemResponse> updateOrderItem(@PathVariable Long orderItemId, @RequestBody OrderItemUpdateRequest request) {
        OrderItemResponse response = orderItemService.updateOrderItem(orderItemId, request);
        return ApiResponse.<OrderItemResponse>builder()
                .result(response)
                .build();
    }

    // Xóa OrderItem
    @DeleteMapping("/{orderItemId}")
    public ApiResponse<String> deleteOrderItem(@PathVariable Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
        return ApiResponse.<String>builder()
                .result("OrderItem has been deleted")
                .build();
    }
}
