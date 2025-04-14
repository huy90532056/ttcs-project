package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.InventoryCreationRequest;
import com.shopee.ecommerce_web.dto.response.InventoryResponse;
import com.shopee.ecommerce_web.service.InventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryController {

    InventoryService inventoryService;

    // Create a new Inventory
    @PostMapping
    public ApiResponse<InventoryResponse> createInventory(@RequestBody InventoryCreationRequest request) {
        InventoryResponse inventoryResponse = inventoryService.createInventory(request);
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryResponse)
                .build();
    }

    // Get all Inventories
    @GetMapping
    public ApiResponse<List<InventoryResponse>> getInventories() {
        List<InventoryResponse> inventories = inventoryService.getInventories();
        return ApiResponse.<List<InventoryResponse>>builder()
                .result(inventories)
                .build();
    }

    // Get Inventory by ID
    @GetMapping("/{inventoryId}")
    public ApiResponse<InventoryResponse> getInventory(@PathVariable Long inventoryId) {
        InventoryResponse inventoryResponse = inventoryService.getInventory(inventoryId);
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryResponse)
                .build();
    }

    // Update an Inventory
    @PutMapping("/{inventoryId}")
    public ApiResponse<InventoryResponse> updateInventory(@PathVariable Long inventoryId, @RequestBody InventoryCreationRequest request) {
        InventoryResponse inventoryResponse = inventoryService.updateInventory(inventoryId, request);
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryResponse)
                .build();
    }

    // Delete an Inventory
    @DeleteMapping("/{inventoryId}")
    public ApiResponse<String> deleteInventory(@PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return ApiResponse.<String>builder()
                .result("Inventory has been deleted")
                .build();
    }
}
