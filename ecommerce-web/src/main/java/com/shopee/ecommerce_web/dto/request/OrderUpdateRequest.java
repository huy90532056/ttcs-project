package com.shopee.ecommerce_web.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateRequest {

    private String status; // Trạng thái đơn hàng (ví dụ: "PENDING", "COMPLETED", "CANCELLED")

    // Bạn có thể thêm các trường khác ở đây nếu cần cập nhật thông tin bổ sung
}
