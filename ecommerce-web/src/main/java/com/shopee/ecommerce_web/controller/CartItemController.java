package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.CartItemCreationRequest;
import com.shopee.ecommerce_web.dto.response.CartItemResponse;
import com.shopee.ecommerce_web.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    // Create a new CartItem
    @PostMapping
    public ApiResponse<CartItemResponse> createCartItem(@RequestBody CartItemCreationRequest request) {
        CartItemResponse cartItemResponse = cartItemService.createCartItem(request);
        return ApiResponse.<CartItemResponse>builder()
                .result(cartItemResponse)
                .build();
    }

    // Get all CartItems
    @GetMapping
    public ApiResponse<List<CartItemResponse>> getAllCartItems() {
        List<CartItemResponse> cartItems = cartItemService.getAllCartItems();
        return ApiResponse.<List<CartItemResponse>>builder()
                .result(cartItems)
                .build();
    }

    // Get CartItem by ID
    @GetMapping("/{cartItemId}")
    public ApiResponse<CartItemResponse> getCartItemById(@PathVariable Long cartItemId) {
        CartItemResponse cartItemResponse = cartItemService.getCartItemById(cartItemId);
        return ApiResponse.<CartItemResponse>builder()
                .result(cartItemResponse)
                .build();
    }

    // Update CartItem
    @PutMapping("/{cartItemId}")
    public ApiResponse<CartItemResponse> updateCartItem(@PathVariable Long cartItemId, @RequestBody CartItemCreationRequest request) {
        CartItemResponse cartItemResponse = cartItemService.updateCartItem(cartItemId, request);
        return ApiResponse.<CartItemResponse>builder()
                .result(cartItemResponse)
                .build();
    }

    // Delete CartItem
    @DeleteMapping("/{cartItemId}")
    public ApiResponse<String> deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return ApiResponse.<String>builder()
                .result("CartItem has been deleted")
                .build();
    }
}
