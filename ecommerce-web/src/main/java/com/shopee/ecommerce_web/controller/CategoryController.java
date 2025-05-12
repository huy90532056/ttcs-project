package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.CategoryDto;
import com.shopee.ecommerce_web.dto.response.CategoryResponse;
import com.shopee.ecommerce_web.entity.FileS3;
import com.shopee.ecommerce_web.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;


    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestParam("categoryName") String categoryName,
                                                        @RequestParam("categoryDescription") String categoryDescription,
                                                        @RequestParam("active") Boolean active,
                                                        @RequestParam("categoryImage") MultipartFile categoryImage) {

        // Tạo CategoryDto từ các tham số
        CategoryDto categoryDto = new CategoryDto(categoryName, categoryDescription, null, active);

        // Gọi service để tạo category
        CategoryResponse response = categoryService.createCategory(categoryDto, categoryImage);

        return ApiResponse.<CategoryResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> responseList = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(responseList)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable String id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ApiResponse.<CategoryResponse>builder()
                .result(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable String id, @RequestBody @Valid CategoryDto categoryDto) {
        CategoryResponse response = categoryService.updateCategory(id, categoryDto);
        return ApiResponse.<CategoryResponse>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .result("Category has been deleted")
                .build();
    }
}
