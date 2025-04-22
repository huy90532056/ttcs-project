package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findAllByUser_Id(String userId);
}
