package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.ShippingTrackerCreationRequest;
import com.shopee.ecommerce_web.dto.request.ShippingTrackerUpdateRequest;
import com.shopee.ecommerce_web.dto.response.ShippingTrackerResponse;
import com.shopee.ecommerce_web.service.ShippingTrackerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingTrackerController {

    ShippingTrackerService shippingTrackerService;

    @PostMapping
    public ApiResponse<ShippingTrackerResponse> createShippingTracker(@RequestBody ShippingTrackerCreationRequest request) {
        ShippingTrackerResponse response = shippingTrackerService.createShippingTracker(request);
        return ApiResponse.<ShippingTrackerResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<ShippingTrackerResponse>> getAllShippingTrackers() {
        List<ShippingTrackerResponse> responses = shippingTrackerService.getShippingTrackers();
        return ApiResponse.<List<ShippingTrackerResponse>>builder()
                .result(responses)
                .build();
    }

    @GetMapping("/{trackingId}")
    public ApiResponse<ShippingTrackerResponse> getShippingTracker(@PathVariable Long trackingId) {
        ShippingTrackerResponse response = shippingTrackerService.getShippingTracker(trackingId);
        return ApiResponse.<ShippingTrackerResponse>builder()
                .result(response)
                .build();
    }

    @PutMapping("/{trackingId}")
    public ApiResponse<ShippingTrackerResponse> updateShippingTracker(@PathVariable Long trackingId, @RequestBody ShippingTrackerUpdateRequest request) {
        ShippingTrackerResponse response = shippingTrackerService.updateShippingTracker(trackingId, request);
        return ApiResponse.<ShippingTrackerResponse>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/{trackingId}")
    public ApiResponse<String> deleteShippingTracker(@PathVariable Long trackingId) {
        shippingTrackerService.deleteShippingTracker(trackingId);
        return ApiResponse.<String>builder()
                .result("Shipping Tracker has been deleted")
                .build();
    }
}
