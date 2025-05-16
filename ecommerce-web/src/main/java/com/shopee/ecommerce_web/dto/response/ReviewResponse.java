package com.shopee.ecommerce_web.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long reviewId;

    private String userId;

    private Long productId;

    private String comment;

    private Integer rating;
}
