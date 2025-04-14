package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.DiscountCreationRequest;
import com.shopee.ecommerce_web.dto.request.DiscountUpdateRequest;
import com.shopee.ecommerce_web.dto.response.DiscountResponse;
import com.shopee.ecommerce_web.service.DiscountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountController {

    DiscountService discountService;

    // Create a new Discount
    @PostMapping
    public ApiResponse<DiscountResponse> createDiscount(@RequestBody DiscountCreationRequest request) {
        DiscountResponse discountResponse = discountService.createDiscount(request);
        return ApiResponse.<DiscountResponse>builder()
                .result(discountResponse)
                .build();
    }

    // Get all Discounts
    @GetMapping
    public ApiResponse<List<DiscountResponse>> getDiscounts() {
        List<DiscountResponse> discounts = discountService.getAllDiscounts();
        return ApiResponse.<List<DiscountResponse>>builder()
                .result(discounts)
                .build();
    }

    // Get Discount by ID
    @GetMapping("/{discountId}")
    public ApiResponse<DiscountResponse> getDiscount(@PathVariable Long discountId) {
        DiscountResponse discountResponse = discountService.getDiscountById(discountId);
        return ApiResponse.<DiscountResponse>builder()
                .result(discountResponse)
                .build();
    }

    // Update a Discount
    @PutMapping("/{discountId}")
    public ApiResponse<DiscountResponse> updateDiscount(@PathVariable Long discountId, @RequestBody DiscountUpdateRequest request) {
        DiscountResponse discountResponse = discountService.updateDiscount(discountId, request);
        return ApiResponse.<DiscountResponse>builder()
                .result(discountResponse)
                .build();
    }

    // Delete a Discount
    @DeleteMapping("/{discountId}")
    public ApiResponse<String> deleteDiscount(@PathVariable Long discountId) {
        discountService.deleteDiscount(discountId);
        return ApiResponse.<String>builder()
                .result("Discount has been deleted")
                .build();
    }
}
