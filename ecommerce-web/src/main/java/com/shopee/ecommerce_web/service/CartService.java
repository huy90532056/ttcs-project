package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.CartCreationRequest;
import com.shopee.ecommerce_web.dto.response.CartItemResponse;
import com.shopee.ecommerce_web.dto.response.CartResponse;
import com.shopee.ecommerce_web.dto.response.CartSummaryResponse;
import com.shopee.ecommerce_web.entity.*;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;

    // Tạo mới giỏ hàng
    public CartResponse createCart(CartCreationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Kiểm tra xem user đã có cart chưa
        Cart existingCart = cartRepository.findByUserId(user.getId()).orElse(null);
        if (existingCart != null) {
            return mapToCartResponse(existingCart);
        }

        // Nếu chưa có thì tạo mới
        Cart cart = new Cart();
        cart.setUser(user);

        cart = cartRepository.save(cart);

        return mapToCartResponse(cart);
    }


    // Lấy thông tin giỏ hàng theo ID
    public CartResponse getCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        return mapToCartResponse(cart);
    }

    // Lấy thông tin tất cả giỏ hàng
    public List<CartResponse> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        return carts.stream()
                .map(this::mapToCartResponse)
                .collect(Collectors.toList());
    }

    // Thêm sản phẩm vào giỏ hàng
    public CartResponse addItemToCart(Long cartId, UUID productVariantId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        ProductVariant productVariant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProductVariant(productVariant);
        cartItem.setQuantity(quantity);

        cartItem = cartItemRepository.save(cartItem);

        cart.getCartItems().add(cartItem);
        cart = cartRepository.save(cart);

        return mapToCartResponse(cart);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public CartResponse removeItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        cart = cartRepository.save(cart);

        return mapToCartResponse(cart);
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public CartResponse updateItemQuantity(Long cartId, Long cartItemId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.setQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);

        return mapToCartResponse(cart);
    }

    // Xóa giỏ hàng
    @Transactional
    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        // Xóa tất cả các CartItem liên quan
        cartItemRepository.deleteAll(cart.getCartItems());

        // Xóa giỏ hàng
        cartRepository.delete(cart);
    }


    // Làm trống giỏ hàng
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    // Thanh toán giỏ hàng
    public boolean checkout(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        // Here you would implement logic like payment processing, order creation, etc.
        // For now, assume checkout is always successful:
        return true;
    }

    // Lấy thông tin tổng quan về giỏ hàng
    public CartSummaryResponse getCartSummary(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        // Calculate the total price, total quantity, etc. for the cart
        double totalPrice = 0.0;
        int totalQuantity = 0;

        for (CartItem item : cart.getCartItems()) {
            double price = item.getProductVariant().getPrice() * item.getQuantity();
            totalPrice += price;
            totalQuantity += item.getQuantity();
        }

        return new CartSummaryResponse(totalQuantity, totalPrice);
    }

    // Phương thức giúp chuyển đổi từ Cart sang CartResponse
    private CartResponse mapToCartResponse(Cart cart) {
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setUserId(cart.getUser().getId());

        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
                .map(cartItem -> new CartItemResponse(
                        cartItem.getCartItemId(),
                        cartItem.getQuantity(),
                        cartItem.getProductVariant().getVariantId(),
                        cartItem.getCart().getCartId()
                ))
                .collect(Collectors.toList());

        cartResponse.setCartItems(cartItemResponses);

        return cartResponse;
    }

    public CartResponse getCartByUserId(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        return mapToCartResponse(cart);
    }

}
