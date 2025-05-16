package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.Inventory;
import com.shopee.ecommerce_web.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
    List<ProductInventory> findByInventory(Inventory inventory);

    List<ProductInventory> findByProduct_ProductId(Long productId);

}
