package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.ReviewRequest;
import com.shopee.ecommerce_web.dto.response.ReviewResponse;
import com.shopee.ecommerce_web.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;

    // ✅ Tạo review mới
    @PostMapping
    public ApiResponse<ReviewResponse> createReview(@RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.createReview(request);
        return ApiResponse.<ReviewResponse>builder()
                .result(response)
                .build();
    }

    // ✅ Xem tất cả review theo productId
    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewResponse> responses = reviewService.getReviewsByProductId(productId);
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(responses)
                .build();
    }

    // ✅ Xoá review
    @DeleteMapping("/{reviewId}")
    public ApiResponse<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResponse.<String>builder()
                .result("Review has been deleted.")
                .build();
    }

    // ✅ Cập nhật review
    @PutMapping("/{reviewId}")
    public ApiResponse<ReviewResponse> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.updateReview(reviewId, request);
        return ApiResponse.<ReviewResponse>builder()
                .result(response)
                .build();
    }

    // ✅ Xem tất cả review
    @GetMapping
    public ApiResponse<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> responses = reviewService.getAllReviews();
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(responses)
                .build();
    }

    // ✅ Xem review theo reviewId
    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewResponse> getReviewById(@PathVariable Long reviewId) {
        ReviewResponse response = reviewService.getReviewById(reviewId);
        return ApiResponse.<ReviewResponse>builder()
                .result(response)
                .build();
    }
}
