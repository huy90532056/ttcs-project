package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
