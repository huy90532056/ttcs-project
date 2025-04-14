package com.shopee.ecommerce_web.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationCreationRequest {
    private String message;
    private String userId;
}
