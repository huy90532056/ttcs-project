package com.shopee.ecommerce_web.repository;
import com.shopee.ecommerce_web.entity.UserDiscount;
import com.shopee.ecommerce_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserDiscountRepository extends JpaRepository<UserDiscount, Long> {
    List<UserDiscount> findAllByUser(User user);
}
