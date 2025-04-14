package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.ProductVariantRequest;
import com.shopee.ecommerce_web.dto.response.ProductVariantResponse;
import com.shopee.ecommerce_web.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    // Tạo mới ProductVariant
    @PostMapping
    public ApiResponse<ProductVariantResponse> createProductVariant(@RequestBody ProductVariantRequest request) {
        ApiResponse<ProductVariantResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productVariantService.createProductVariant(request));
        return apiResponse;
    }

    // Lấy danh sách tất cả ProductVariants
    @GetMapping
    public ApiResponse<List<ProductVariantResponse>> getAllProductVariants() {
        ApiResponse<List<ProductVariantResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productVariantService.getAllProductVariants());
        return apiResponse;
    }

    // Lấy ProductVariant theo variantId
    @GetMapping("/{variantId}")
    public ApiResponse<ProductVariantResponse> getProductVariant(@PathVariable("variantId") UUID variantId) {
        ApiResponse<ProductVariantResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productVariantService.getProductVariantById(variantId));
        return apiResponse;
    }

    // Cập nhật ProductVariant
    @PutMapping("/{variantId}")
    public ApiResponse<ProductVariantResponse> updateProductVariant(@PathVariable("variantId") UUID variantId,
                                                                    @RequestBody ProductVariantRequest request) {
        ApiResponse<ProductVariantResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productVariantService.updateProductVariant(variantId, request));
        return apiResponse;
    }

    // Xóa ProductVariant
    @DeleteMapping("/{variantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductVariant(@PathVariable("variantId") UUID variantId) {
        productVariantService.deleteProductVariant(variantId);
    }
}
