package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.OrderItemCreationRequest;
import com.shopee.ecommerce_web.dto.request.OrderItemUpdateRequest;
import com.shopee.ecommerce_web.dto.response.OrderItemResponse;
import com.shopee.ecommerce_web.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    // Tạo mới OrderItem
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemResponse createOrderItem(@RequestBody OrderItemCreationRequest request) {
        return orderItemService.createOrderItem(request);
    }

    // Lấy tất cả OrderItems
    @GetMapping
    public List<OrderItemResponse> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    // Lấy OrderItem theo ID
    @GetMapping("/{orderItemId}")
    public OrderItemResponse getOrderItem(@PathVariable Long orderItemId) {
        return orderItemService.getOrderItem(orderItemId);
    }

    // Cập nhật OrderItem
    @PutMapping("/{orderItemId}")
    public OrderItemResponse updateOrderItem(@PathVariable Long orderItemId, @RequestBody OrderItemUpdateRequest request) {
        return orderItemService.updateOrderItem(orderItemId, request);
    }

    // Xóa OrderItem
    @DeleteMapping("/{orderItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItem(@PathVariable Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
    }
}
