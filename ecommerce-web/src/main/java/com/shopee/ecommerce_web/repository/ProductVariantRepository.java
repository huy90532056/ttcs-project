package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {

    ProductVariant findByVariantName(String variantName);
}
