package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.UserDiscountRequest;
import com.shopee.ecommerce_web.dto.response.UserDiscountResponse;
import com.shopee.ecommerce_web.entity.Discount;
import com.shopee.ecommerce_web.entity.User;
import com.shopee.ecommerce_web.entity.UserDiscount;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.DiscountRepository;
import com.shopee.ecommerce_web.repository.UserDiscountRepository;
import com.shopee.ecommerce_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDiscountService {

    private final UserDiscountRepository userDiscountRepository;
    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;

    // Gán discount cho user từ request DTO
    public UserDiscountResponse assignDiscountToUser(UserDiscountRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Discount discount = discountRepository.findById(request.getDiscountId())
                .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));

        UserDiscount userDiscount = UserDiscount.builder()
                .user(user)
                .discount(discount)
                .isUsed(false)
                .build();

        UserDiscount saved = userDiscountRepository.save(userDiscount);
        return convertToResponse(saved);
    }

    // Lấy danh sách discount của user theo userId
    public List<UserDiscountResponse> getDiscountsByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<UserDiscount> userDiscounts = userDiscountRepository.findAllByUser(user);
        return userDiscounts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Đánh dấu discount đã được sử dụng
    public UserDiscountResponse markDiscountUsed(Long userDiscountId) {
        UserDiscount userDiscount = userDiscountRepository.findById(userDiscountId)
                .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));

        userDiscount.setIsUsed(true);
        userDiscount.setUsedAt(new Date());

        UserDiscount updated = userDiscountRepository.save(userDiscount);
        return convertToResponse(updated);
    }

    // Xóa mapping user-discount
    public void deleteUserDiscount(Long userDiscountId) {
        if (!userDiscountRepository.existsById(userDiscountId)) {
            throw new AppException(ErrorCode.USER_DISCOUNT_NOT_FOUND);
        }
        userDiscountRepository.deleteById(userDiscountId);
    }

    // Chuyển entity UserDiscount sang DTO
    private UserDiscountResponse convertToResponse(UserDiscount userDiscount) {
        return UserDiscountResponse.builder()
                .id(userDiscount.getId())
                .userId(userDiscount.getUser().getId())
                .discountId(userDiscount.getDiscount().getDiscountId())
                .isUsed(userDiscount.getIsUsed())
                .usedAt(userDiscount.getUsedAt())
                .build();
    }
}
