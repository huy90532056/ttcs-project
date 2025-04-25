package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.Order;
import com.shopee.ecommerce_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
