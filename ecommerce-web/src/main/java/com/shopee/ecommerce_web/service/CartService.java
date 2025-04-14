package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.CartCreationRequest;
import com.shopee.ecommerce_web.dto.response.CartItemResponse;
import com.shopee.ecommerce_web.dto.response.CartResponse;
import com.shopee.ecommerce_web.entity.Cart;
import com.shopee.ecommerce_web.entity.CartItem;
import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.entity.User;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.CartItemRepository;
import com.shopee.ecommerce_web.repository.CartRepository;
import com.shopee.ecommerce_web.repository.ProductRepository;
import com.shopee.ecommerce_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository; // Thêm UserRepository để truy vấn User từ userId

    // Tạo mới giỏ hàng
    public CartResponse createCart(CartCreationRequest request) {
        // Tìm User từ userId
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Cart cart = new Cart();
        cart.setUser(user);

        // Lưu giỏ hàng vào cơ sở dữ liệu
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

    // Cập nhật giỏ hàng (ví dụ: thêm sản phẩm vào giỏ hàng)
    public CartResponse addItemToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        // Lưu sản phẩm vào giỏ hàng
        cartItem = cartItemRepository.save(cartItem);

        // Cập nhật lại danh sách các sản phẩm trong giỏ hàng
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

        cart.getCartItems().remove(cartItem); // Xóa sản phẩm khỏi giỏ hàng
        cartItemRepository.delete(cartItem); // Xóa sản phẩm khỏi cơ sở dữ liệu

        // Lưu lại giỏ hàng sau khi xóa sản phẩm
        cart = cartRepository.save(cart);

        return mapToCartResponse(cart);
    }

    // Xóa giỏ hàng
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    // Phương thức giúp chuyển đổi từ Cart sang CartResponse
    private CartResponse mapToCartResponse(Cart cart) {
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>()); // Khởi tạo nếu là null
        }
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setUserId(cart.getUser().getId());

        // Chuyển đổi các CartItem thành CartItemResponse
        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
                .map(cartItem -> new CartItemResponse(
                        cartItem.getCartItemId(),         // cartItemId
                        cartItem.getQuantity(),           // quantity
                        cartItem.getProduct().getProductId(), // productId
                        cartItem.getCart().getCartId()    // cartId
                ))
                .collect(Collectors.toList());

        cartResponse.setCartItems(cartItemResponses);

        return cartResponse;
    }

}
