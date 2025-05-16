package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.*;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    @NotNull(message = "UserId is required")
    private String userId;

    @NotNull(message = "ProductId is required")
    private Long productId;

    @NotBlank(message = "Comment cannot be blank")
    private String comment;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;
}
