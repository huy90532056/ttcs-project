package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.Cart;
import com.shopee.ecommerce_web.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);

    // Xoá tất cả CartItem theo Cart
    void deleteAllByCart(Cart cart);
}
