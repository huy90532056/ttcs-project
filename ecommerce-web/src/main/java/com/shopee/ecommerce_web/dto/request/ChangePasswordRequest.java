package com.shopee.ecommerce_web.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String id;
    private String newPassword;

}