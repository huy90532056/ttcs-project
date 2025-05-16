package com.shopee.ecommerce_web.repository;

import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProduct(Product product);

}