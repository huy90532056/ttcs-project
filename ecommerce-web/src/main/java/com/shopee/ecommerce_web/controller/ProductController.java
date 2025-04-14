package com.shopee.ecommerce_web.controller;
import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.ProductCreationRequest;
import com.shopee.ecommerce_web.dto.request.ProductUpdateRequest;
import com.shopee.ecommerce_web.dto.response.PageResponse;
import com.shopee.ecommerce_web.dto.response.ProductResponse;
import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping()
    ApiResponse<Product> createProduct(@RequestBody @Valid ProductCreationRequest request) {

        ApiResponse<Product> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.createProduct(request));

        return apiResponse;
    }

    @GetMapping()
    List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    ProductResponse getProduct(@PathVariable("productId") Long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping("{productId}")
    ProductResponse updateProduct(@PathVariable("productId") Long productId,
                                  @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    String deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return "User has been deleted";
    }

    @PutMapping("/{productId}/categories/{categoryId}")
    public ApiResponse<Product> addCategoryToProduct(@PathVariable("productId") Long productId,
                                                     @PathVariable("categoryId") String categoryId) {
        ApiResponse<Product> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.addCategoryToProduct(productId, categoryId));
        return apiResponse;
    }

    @PutMapping("/{productId}/tags/{tagId}")
    public ApiResponse<Product> addTagToProduct(@PathVariable("productId") Long productId,
                                                @PathVariable("tagId") String tagId) {
        ApiResponse<Product> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.addTagToProduct(productId, tagId));
        return apiResponse;
    }

    @GetMapping("/list")
    public ApiResponse<List<ProductResponse>> getAllProductsPaging(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.getAllProductsPaging(pageNo, pageSize));
        return apiResponse;
    }

    @GetMapping("/list/sort")
    public ApiResponse<List<ProductResponse>> getAllProductsPagingSort(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.getAllProductsPagingSort(pageNo, pageSize, sortBy));
        return apiResponse;
    }

    @GetMapping("/list/sort/multiple")
    public ApiResponse<PageResponse<?>> getAllProductsPagingSortByMultipleCategory(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String... sort
    ) {
        ApiResponse<PageResponse<?>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.getAllProductsPagingSortByMultipleCategory(pageNo, pageSize, sort));
        return apiResponse;
    }

    @GetMapping("/list/sort/multiple/search")
    public ApiResponse<PageResponse<?>> getAllProductsPagingSortByMultipleCategorySearch(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy
    ) {
        ApiResponse<PageResponse<?>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.getAllProductsPagingSortByMultipleCategorySearch(pageNo, pageSize,
                search, sortBy));
        return apiResponse;

    }
}