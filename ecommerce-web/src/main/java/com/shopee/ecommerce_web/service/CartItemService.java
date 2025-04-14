package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.CartItemCreationRequest;
import com.shopee.ecommerce_web.dto.response.CartItemResponse;
import com.shopee.ecommerce_web.entity.Cart;
import com.shopee.ecommerce_web.entity.CartItem;
import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.CartItemRepository;
import com.shopee.ecommerce_web.repository.CartRepository;
import com.shopee.ecommerce_web.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartItemResponse createCartItem(CartItemCreationRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(request.getQuantity());
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        cartItem = cartItemRepository.save(cartItem);

        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setQuantity(cartItem.getQuantity());
        response.setProductId(cartItem.getProduct().getProductId());
        response.setCartId(cartItem.getCart().getCartId());

        return response;
    }

    public List<CartItemResponse> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .map(cartItem -> {
                    CartItemResponse response = new CartItemResponse();
                    response.setCartItemId(cartItem.getCartItemId());
                    response.setQuantity(cartItem.getQuantity());
                    response.setProductId(cartItem.getProduct().getProductId());
                    response.setCartId(cartItem.getCart().getCartId());
                    return response;
                })
                .toList();
    }

    public CartItemResponse getCartItemById(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setQuantity(cartItem.getQuantity());
        response.setProductId(cartItem.getProduct().getProductId());
        response.setCartId(cartItem.getCart().getCartId());

        return response;
    }

    public CartItemResponse updateCartItem(Long cartItemId, CartItemCreationRequest request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.setQuantity(request.getQuantity());

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        cartItem.setProduct(product);

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        cartItem.setCart(cart);

        cartItem = cartItemRepository.save(cartItem);

        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setQuantity(cartItem.getQuantity());
        response.setProductId(cartItem.getProduct().getProductId());
        response.setCartId(cartItem.getCart().getCartId());

        return response;
    }

    public void deleteCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND);
        }
        cartItemRepository.deleteById(cartItemId);
    }
}
