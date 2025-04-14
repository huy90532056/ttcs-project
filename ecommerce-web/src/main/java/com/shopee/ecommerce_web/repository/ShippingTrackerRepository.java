package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.ShippingTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingTrackerRepository extends JpaRepository<ShippingTracker, Long> {
}
