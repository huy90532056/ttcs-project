package com.shopee.ecommerce_web.dto.response;

import com.shopee.ecommerce_web.entity.ProductInventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    private Long inventoryId;
    private String userId;
    private String inventoryImagePath;
    private List<ProductInventory> productInventories;
}
