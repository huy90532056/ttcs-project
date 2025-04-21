package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
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
    public ApiResponse<ProductInventoryResponse> createProductInventory(@RequestBody ProductInventoryCreationRequest request) {
        ProductInventoryResponse response = productInventoryService.createProductInventory(request);
        return ApiResponse.<ProductInventoryResponse>builder()
                .result(response)
                .build();
    }

    // Get all ProductInventories
    @GetMapping
    public ApiResponse<List<ProductInventoryResponse>> getAllProductInventories() {
        List<ProductInventoryResponse> responses = productInventoryService.getAllProductInventories();
        return ApiResponse.<List<ProductInventoryResponse>>builder()
                .result(responses)
                .build();
    }

    // Get a ProductInventory by ID
    @GetMapping("/{productInventoryId}")
    public ApiResponse<ProductInventoryResponse> getProductInventory(@PathVariable Long productInventoryId) {
        ProductInventoryResponse response = productInventoryService.getProductInventoryById(productInventoryId);
        return ApiResponse.<ProductInventoryResponse>builder()
                .result(response)
                .build();
    }

    // Update ProductInventory
    @PutMapping("/{productInventoryId}")
    public ApiResponse<ProductInventoryResponse> updateProductInventory(@PathVariable Long productInventoryId,
                                                                        @RequestBody ProductInventoryUpdateRequest request) {
        ProductInventoryResponse response = productInventoryService.updateProductInventory(productInventoryId, request);
        return ApiResponse.<ProductInventoryResponse>builder()
                .result(response)
                .build();
    }

    // Delete ProductInventory
    @DeleteMapping("/{productInventoryId}")
    public ApiResponse<String> deleteProductInventory(@PathVariable Long productInventoryId) {
        productInventoryService.deleteProductInventory(productInventoryId);
        return ApiResponse.<String>builder()
                .result("ProductInventory has been deleted")
                .build();
    }
}
