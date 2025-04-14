package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
