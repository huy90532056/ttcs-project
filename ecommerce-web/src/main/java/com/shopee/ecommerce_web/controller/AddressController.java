package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.AddressRequest;
import com.shopee.ecommerce_web.dto.response.AddressResponse;
import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.service.AddressService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressController {

    AddressService addressService;

    @PostMapping
    ApiResponse<AddressResponse> createAddress(@RequestBody @Valid AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.createAddress(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<AddressResponse>> getAllAddresses() {
        return ApiResponse.<List<AddressResponse>>builder()
                .result(addressService.getAllAddresses())
                .build();
    }

    @GetMapping("/{addressId}")
    ApiResponse<AddressResponse> getAddress(@PathVariable String addressId) {
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.getAddress(addressId))
                .build();
    }

    @PutMapping("/{addressId}")
    ApiResponse<AddressResponse> updateAddress(@PathVariable String addressId, @RequestBody @Valid AddressRequest request) {
        return ApiResponse.<AddressResponse>builder()
                .result(addressService.updateAddress(addressId, request))
                .build();
    }

    @DeleteMapping("/{addressId}")
    ApiResponse<String> deleteAddress(@PathVariable String addressId) {
        addressService.deleteAddress(addressId);
        return ApiResponse.<String>builder()
                .result("Address has been deleted")
                .build();
    }
}
