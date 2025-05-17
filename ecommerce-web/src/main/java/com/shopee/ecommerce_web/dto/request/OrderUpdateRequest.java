package com.shopee.ecommerce_web.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateRequest {

    private String status;
    private String shipperId;
}
