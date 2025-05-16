package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.UserDiscountRequest;
import com.shopee.ecommerce_web.dto.response.UserDiscountResponse;
import com.shopee.ecommerce_web.service.UserDiscountService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-discounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDiscountController {

    UserDiscountService userDiscountService;

    // Tạo mapping user - discount (assign discount cho user)
    @PostMapping
    public ApiResponse<UserDiscountResponse> createUserDiscount(@RequestBody UserDiscountRequest request) {
        UserDiscountResponse response = userDiscountService.assignDiscountToUser(request);
        return ApiResponse.<UserDiscountResponse>builder()
                .result(response)
                .build();
    }

    // Lấy tất cả discount của user theo userId
    @GetMapping("/user/{userId}")
    public ApiResponse<List<UserDiscountResponse>> getDiscountsByUser(@PathVariable String userId) {
        List<UserDiscountResponse> discounts = userDiscountService.getDiscountsByUserId(userId);
        return ApiResponse.<List<UserDiscountResponse>>builder()
                .result(discounts)
                .build();
    }

    // Cập nhật trạng thái isUsed của userDiscount theo id
    @PutMapping("/{userDiscountId}/use")
    public ApiResponse<UserDiscountResponse> markAsUsed(@PathVariable Long userDiscountId) {
        UserDiscountResponse response = userDiscountService.markDiscountUsed(userDiscountId);
        return ApiResponse.<UserDiscountResponse>builder()
                .result(response)
                .build();
    }

    // Xóa mapping user-discount theo id
    @DeleteMapping("/{userDiscountId}")
    public ApiResponse<String> deleteUserDiscount(@PathVariable Long userDiscountId) {
        userDiscountService.deleteUserDiscount(userDiscountId);
        return ApiResponse.<String>builder()
                .result("UserDiscount mapping deleted")
                .build();
    }
}
