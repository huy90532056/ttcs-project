package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategories_CategoryId(String categoryId);

}
