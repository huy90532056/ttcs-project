package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.CartCreationRequest;
import com.shopee.ecommerce_web.dto.response.CartResponse;
import com.shopee.ecommerce_web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Tạo giỏ hàng mới
    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody CartCreationRequest request) {
        CartResponse cartResponse = cartService.createCart(request);
        return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
    }

    // Lấy thông tin giỏ hàng theo cartId
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long cartId) {
        CartResponse cartResponse = cartService.getCart(cartId);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    // Lấy thông tin tất cả giỏ hàng
    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        List<CartResponse> cartResponses = cartService.getAllCarts();
        return new ResponseEntity<>(cartResponses, HttpStatus.OK);
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartResponse> addItemToCart(@PathVariable Long cartId,
                                                      @RequestParam Long productId,
                                                      @RequestParam Integer quantity) {
        CartResponse cartResponse = cartService.addItemToCart(cartId, productId, quantity);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable Long cartId,
                                                           @PathVariable Long cartItemId) {
        CartResponse cartResponse = cartService.removeItemFromCart(cartId, cartItemId);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    // Xóa giỏ hàng
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
