package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.ProductCreationRequest;
import com.shopee.ecommerce_web.dto.request.ProductUpdateRequest;
import com.shopee.ecommerce_web.dto.response.PageResponse;
import com.shopee.ecommerce_web.dto.response.ProductResponse;
import com.shopee.ecommerce_web.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Tạo sản phẩm mới
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> createProduct(@ModelAttribute @Valid ProductCreationRequest request) {
        ProductResponse productResponse = productService.createProduct(request);
        return ApiResponse.<ProductResponse>builder()
                .result(productResponse)
                .build();
    }

    // Lấy tất cả sản phẩm
    @GetMapping()
    public ApiResponse<List<ProductResponse>> getProducts() {
        List<ProductResponse> productResponses = productService.getProducts();
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productResponses)
                .build();
    }

    // Lấy thông tin sản phẩm theo id
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable("productId") Long productId) {
        ProductResponse productResponse = productService.getProduct(productId);
        return ApiResponse.<ProductResponse>builder()
                .result(productResponse)
                .build();
    }

    // Cập nhật sản phẩm
    @PutMapping("{productId}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable("productId") Long productId,
                                                      @RequestBody ProductUpdateRequest request) {
        ProductResponse productResponse = productService.updateProduct(productId, request);
        return ApiResponse.<ProductResponse>builder()
                .result(productResponse)
                .build();
    }

    // Xóa sản phẩm
    @DeleteMapping("/{productId}")
    public ApiResponse<String> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.<String>builder()
                .result("Product has been deleted")
                .build();
    }

    // Thêm category vào sản phẩm
    @PutMapping("/{productId}/categories/{categoryId}")
    public ApiResponse<ProductResponse> addCategoryToProduct(@PathVariable("productId") Long productId,
                                                             @PathVariable("categoryId") String categoryId) {
        ProductResponse productResponse = productService.addCategoryToProduct(productId, categoryId);
        return ApiResponse.<ProductResponse>builder()
                .result(productResponse)
                .build();
    }

    // Thêm tag vào sản phẩm
    @PutMapping("/{productId}/tags/{tagId}")
    public ApiResponse<ProductResponse> addTagToProduct(@PathVariable("productId") Long productId,
                                                        @PathVariable("tagId") String tagId) {
        ProductResponse productResponse = productService.addTagToProduct(productId, tagId);
        return ApiResponse.<ProductResponse>builder()
                .result(productResponse)
                .build();
    }

    // Lấy danh sách sản phẩm với phân trang
    @GetMapping("/list")
    public ApiResponse<List<ProductResponse>> getAllProductsPaging(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        List<ProductResponse> productResponses = productService.getAllProductsPaging(pageNo, pageSize);
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productResponses)
                .build();
    }

    // Lấy danh sách sản phẩm với phân trang và sắp xếp
    @GetMapping("/list/sort")
    public ApiResponse<List<ProductResponse>> getAllProductsPagingSort(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        List<ProductResponse> productResponses = productService.getAllProductsPagingSort(pageNo, pageSize, sortBy);
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productResponses)
                .build();
    }

    // Lấy danh sách sản phẩm với phân trang, sắp xếp và nhiều category
    @GetMapping("/list/sort/multiple")
    public ApiResponse<PageResponse<?>> getAllProductsPagingSortByMultipleCategory(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String... sort
    ) {
        PageResponse<?> pageResponse = productService.getAllProductsPagingSortByMultipleCategory(pageNo, pageSize, sort);
        return ApiResponse.<PageResponse<?>>builder()
                .result(pageResponse)
                .build();
    }

    // Lấy danh sách sản phẩm với phân trang, sắp xếp, tìm kiếm và nhiều category
    @GetMapping("/list/sort/multiple/search")
    public ApiResponse<PageResponse<?>> getAllProductsPagingSortByMultipleCategorySearch(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy
    ) {
        PageResponse<?> pageResponse = productService.getAllProductsPagingSortByMultipleCategorySearch(pageNo, pageSize, search, sortBy);
        return ApiResponse.<PageResponse<?>>builder()
                .result(pageResponse)
                .build();
    }
    // Lấy tất cả sản phẩm theo category
    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable("categoryId") String categoryId) {
        List<ProductResponse> products = productService.getProductsByCategory(categoryId);
        return ApiResponse.<List<ProductResponse>>builder()
                .result(products)
                .build();
    }

    // Lấy tất cả sản phẩm theo tag
    @GetMapping("/tag/{tagId}")
    public ApiResponse<List<ProductResponse>> getProductsByTag(@PathVariable("tagId") String tagId) {
        List<ProductResponse> products = productService.getProductsByTag(tagId);
        return ApiResponse.<List<ProductResponse>>builder()
                .result(products)
                .build();
    }

}
