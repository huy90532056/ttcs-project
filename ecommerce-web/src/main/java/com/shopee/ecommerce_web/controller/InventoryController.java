package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.InventoryCreationRequest;
import com.shopee.ecommerce_web.dto.response.InventoryResponse;
import com.shopee.ecommerce_web.service.InventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryController {

    InventoryService inventoryService;

    // Create a new Inventory
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<InventoryResponse> createInventory(@ModelAttribute InventoryCreationRequest request) {
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

    // Delete an Inventory
    @DeleteMapping("/{inventoryId}")
    public ApiResponse<String> deleteInventory(@PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return ApiResponse.<String>builder()
                .result("Inventory has been deleted")
                .build();
    }
    @GetMapping("/user/{userId}")
    public ApiResponse<List<InventoryResponse>> getInventoriesByUserId(@PathVariable String userId) {
        List<InventoryResponse> responses = inventoryService.getInventoriesByUserId(userId);
        return ApiResponse.<List<InventoryResponse>>builder()
                .result(responses)
                .build();
    }
}
