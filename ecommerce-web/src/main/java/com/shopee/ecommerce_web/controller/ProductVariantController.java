package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.ProductCreationRequest;
import com.shopee.ecommerce_web.dto.request.ProductVariantRequest;
import com.shopee.ecommerce_web.dto.response.ProductResponse;
import com.shopee.ecommerce_web.dto.response.ProductVariantResponse;
import com.shopee.ecommerce_web.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    // Tạo mới ProductVariant
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductVariantResponse> createProductVariant(@ModelAttribute @Valid ProductVariantRequest request) {
        System.out.println(request.getVariantValue() + " " + request.getVariantName() + request.getProductVariantImageFile() + " " + request.getPrice());
        ProductVariantResponse response = productVariantService.createProductVariant(request);

        return ApiResponse.<ProductVariantResponse>builder()
                .result(response)
                .build();
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
    public ApiResponse<String> deleteProductVariant(@PathVariable("variantId") UUID variantId) {
        productVariantService.deleteProductVariant(variantId);
        return ApiResponse.<String>builder()
                .result("ProductVariant has been deleted")
                .build();
    }


    @GetMapping("/product/{productId}")
    public ApiResponse<List<ProductVariantResponse>> getProductVariantsByProductId(@PathVariable("productId") Long productId) {
        List<ProductVariantResponse> responses = productVariantService.getProductVariantsByProductId(productId);
        return ApiResponse.<List<ProductVariantResponse>>builder()
                .result(responses)
                .build();
    }
}
