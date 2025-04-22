package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(String userId);
}
