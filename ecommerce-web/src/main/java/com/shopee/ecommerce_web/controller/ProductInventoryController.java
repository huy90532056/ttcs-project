package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ProductInventoryCreationRequest;
import com.shopee.ecommerce_web.dto.request.ProductInventoryUpdateRequest;
import com.shopee.ecommerce_web.dto.response.ProductInventoryResponse;
import com.shopee.ecommerce_web.service.ProductInventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-inventories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductInventoryController {

    ProductInventoryService productInventoryService;

    // Create a new ProductInventory
    @PostMapping
    public ProductInventoryResponse createProductInventory(@RequestBody ProductInventoryCreationRequest request) {
        ProductInventoryResponse response = productInventoryService.createProductInventory(request);
        return response;
    }

    // Get all ProductInventories
    @GetMapping
    public List<ProductInventoryResponse> getAllProductInventories() {
        return productInventoryService.getAllProductInventories();
    }

    // Get a ProductInventory by ID
    @GetMapping("/{productInventoryId}")
    public ProductInventoryResponse getProductInventory(@PathVariable Long productInventoryId) {
        return productInventoryService.getProductInventoryById(productInventoryId);
    }

    // Update ProductInventory
    @PutMapping("/{productInventoryId}")
    public ProductInventoryResponse updateProductInventory(@PathVariable Long productInventoryId,
                                                           @RequestBody ProductInventoryUpdateRequest request) {
        return productInventoryService.updateProductInventory(productInventoryId, request);
    }

    // Delete ProductInventory
    @DeleteMapping("/{productInventoryId}")
    public String deleteProductInventory(@PathVariable Long productInventoryId) {
        productInventoryService.deleteProductInventory(productInventoryId);
        return "ProductInventory has been deleted";
    }
}
