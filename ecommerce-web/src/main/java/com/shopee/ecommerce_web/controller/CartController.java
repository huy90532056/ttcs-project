package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.CartCreationRequest;
import com.shopee.ecommerce_web.dto.response.CartResponse;
import com.shopee.ecommerce_web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                                   @RequestParam Long productId,
                                                   @RequestParam Integer quantity) {
        CartResponse cartResponse = cartService.addItemToCart(cartId, productId, quantity);
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
}