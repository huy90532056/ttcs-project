package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.CartItemCreationRequest;
import com.shopee.ecommerce_web.dto.response.CartItemResponse;
import com.shopee.ecommerce_web.entity.Cart;
import com.shopee.ecommerce_web.entity.CartItem;
import com.shopee.ecommerce_web.entity.ProductVariant;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.CartItemRepository;
import com.shopee.ecommerce_web.repository.CartRepository;
import com.shopee.ecommerce_web.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartRepository cartRepository;

    public CartItemResponse createCartItem(CartItemCreationRequest request) {
        ProductVariant productVariant = productVariantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(request.getQuantity());
        cartItem.setProductVariant(productVariant);
        cartItem.setCart(cart);

        cartItem = cartItemRepository.save(cartItem);

        return toResponse(cartItem);
    }

    public List<CartItemResponse> getAllCartItems() {
        return cartItemRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CartItemResponse getCartItemById(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        return toResponse(cartItem);
    }

    public List<CartItemResponse> getCartItemsByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        return cartItemRepository.findByCart(cart)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CartItemResponse incrementQuantity(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        return toResponse(cartItemRepository.save(cartItem));
    }

    public CartItemResponse decrementQuantity(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        if (cartItem.getQuantity() <= 1) {
            cartItemRepository.delete(cartItem);
            throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND); // Hoặc trả null/empty nếu thích
        }
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        return toResponse(cartItemRepository.save(cartItem));
    }

    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        cartItemRepository.deleteAllByCart(cart);
    }

    public CartItemResponse updateCartItem(Long cartItemId, CartItemCreationRequest request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        ProductVariant productVariant = productVariantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
        cartItem.setProductVariant(productVariant);

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        cartItem.setCart(cart);

        cartItem.setQuantity(request.getQuantity());

        return toResponse(cartItemRepository.save(cartItem));
    }

    public void deleteCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND);
        }
        cartItemRepository.deleteById(cartItemId);
    }

    private CartItemResponse toResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setQuantity(cartItem.getQuantity());
        response.setVariantId(cartItem.getProductVariant().getVariantId());
        response.setCartId(cartItem.getCart().getCartId());
        return response;
    }
}
