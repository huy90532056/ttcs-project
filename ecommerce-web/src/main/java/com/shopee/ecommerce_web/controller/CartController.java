package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.CartCreationRequest;
import com.shopee.ecommerce_web.dto.response.CartResponse;
import com.shopee.ecommerce_web.dto.response.CartSummaryResponse;
import com.shopee.ecommerce_web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Tạo giỏ hàng mới
    @PostMapping
    public ApiResponse<CartResponse> createCart(@RequestBody CartCreationRequest request) {
        CartResponse cartResponse = cartService.createCart(request);
        return ApiResponse.<CartResponse>builder()
                .result(cartResponse)
                .build();
    }

    // Lấy thông tin giỏ hàng theo cartId
    @GetMapping("/{cartId}")
    public ApiResponse<CartResponse> getCart(@PathVariable Long cartId) {
        CartResponse cartResponse = cartService.getCart(cartId);
        return ApiResponse.<CartResponse>builder()
                .result(cartResponse)
                .build();
    }

    // Lấy thông tin tất cả giỏ hàng
    @GetMapping
    public ApiResponse<List<CartResponse>> getAllCarts() {
        List<CartResponse> cartResponses = cartService.getAllCarts();
        return ApiResponse.<List<CartResponse>>builder()
                .result(cartResponses)
                .build();
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/{cartId}/items")
    public ApiResponse<CartResponse> addItemToCart(@PathVariable Long cartId,
                                                   @RequestParam UUID variantId,
                                                   @RequestParam Integer quantity) {
        CartResponse cartResponse = cartService.addItemToCart(cartId, variantId, quantity);
        return ApiResponse.<CartResponse>builder()
                .result(cartResponse)
                .build();
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ApiResponse<CartResponse> removeItemFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long cartItemId) {
        CartResponse cartResponse = cartService.removeItemFromCart(cartId, cartItemId);
        return ApiResponse.<CartResponse>builder()
                .result(cartResponse)
                .build();
    }

    // Xóa giỏ hàng
    @DeleteMapping("/{cartId}")
    public ApiResponse<String> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ApiResponse.<String>builder()
                .result("Cart has been deleted")
                .build();
    }

    @PutMapping("/{cartId}/items/{cartItemId}")
    public ApiResponse<CartResponse> updateItemQuantity(@PathVariable Long cartId,
                                                        @PathVariable Long cartItemId,
                                                        @RequestParam Integer quantity) {
        CartResponse cartResponse = cartService.updateItemQuantity(cartId, cartItemId, quantity);
        return ApiResponse.<CartResponse>builder()
                .result(cartResponse)
                .build();
    }
    // Làm trống giỏ hàng
    @DeleteMapping("/{cartId}/clear")
    public ApiResponse<String> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ApiResponse.<String>builder()
                .result("Cart has been cleared")
                .build();
    }
    @PostMapping("/{cartId}/checkout")
    public ApiResponse<String> checkout(@PathVariable Long cartId) {
        boolean success = cartService.checkout(cartId);
        if (success) {
            return ApiResponse.<String>builder()
                    .result("Checkout successful")
                    .build();
        } else {
            return ApiResponse.<String>builder()
                    .result("Checkout failed")
                    .build();
        }
    }
    @GetMapping("/{cartId}/summary")
    public ApiResponse<CartSummaryResponse> getCartSummary(@PathVariable Long cartId) {
        CartSummaryResponse summary = cartService.getCartSummary(cartId);
        return ApiResponse.<CartSummaryResponse>builder()
                .result(summary)
                .build();
    }
    @GetMapping("/user/{userId}")
    public ApiResponse<CartResponse> getCartByUserId(@PathVariable String userId) {
        CartResponse cartResponse = cartService.getCartByUserId(userId);
        return ApiResponse.<CartResponse>builder()
                .result(cartResponse)
                .build();
    }

}