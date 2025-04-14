package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventoryResponse {
    private Long productInventoryId;  // ID của ProductInventory
    private Long productId;  // ID của Product
    private Long inventoryId;  // ID của Inventory
    private Integer quantity;  // Số lượng sản phẩm
}
