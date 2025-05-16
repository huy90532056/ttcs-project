package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.ReviewRequest;
import com.shopee.ecommerce_web.dto.response.ReviewResponse;
import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.entity.Review;
import com.shopee.ecommerce_web.entity.User;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.ProductRepository;
import com.shopee.ecommerce_web.repository.ReviewRepository;
import com.shopee.ecommerce_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // ✅ Tạo review mới
    public ReviewResponse createReview(ReviewRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Review review = Review.builder()
                .user(user)
                .product(product)
                .comment(request.getComment())
                .rating(request.getRating())
                .build();

        Review savedReview = reviewRepository.save(review);

        return convertToResponse(savedReview);
    }

    // ✅ Lấy tất cả review
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Lấy review theo ID
    public ReviewResponse getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        return convertToResponse(review);
    }

    // ✅ Lấy review theo productId
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        List<Review> reviews = reviewRepository.findAllByProduct(product);
        return reviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Cập nhật review
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        review.setComment(request.getComment());
        review.setRating(request.getRating());


        Review updated = reviewRepository.save(review);
        return convertToResponse(updated);
    }

    // ✅ Xoá review
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }
        reviewRepository.deleteById(reviewId);
    }

    // Helper: chuyển từ Entity sang DTO
    private ReviewResponse convertToResponse(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .userId(review.getUser().getId())
                .productId(review.getProduct().getProductId())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }
}
