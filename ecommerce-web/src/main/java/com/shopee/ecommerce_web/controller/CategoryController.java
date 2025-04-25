package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.CategoryDto;
import com.shopee.ecommerce_web.dto.response.CategoryResponse;
import com.shopee.ecommerce_web.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
=======
import lombok.experimental.FieldDefaults;
>>>>>>> huy
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @PostMapping
<<<<<<< HEAD
    public ApiResponse<Category> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        Category category = categoryService.createCategory(categoryDto);
        return ApiResponse.<Category>builder()
                .result(category)
=======
    public ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        CategoryResponse response = categoryService.createCategory(categoryDto);
        return ApiResponse.<CategoryResponse>builder()
                .result(response)
>>>>>>> huy
                .build();
    }

    @GetMapping
<<<<<<< HEAD
    public ApiResponse<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ApiResponse.<List<Category>>builder()
                .result(categories)
=======
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> responseList = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(responseList)
>>>>>>> huy
                .build();
    }

    @GetMapping("/{id}")
<<<<<<< HEAD
    public ApiResponse<Category> getCategoryById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id);
        return ApiResponse.<Category>builder()
                .result(category)
=======
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable String id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ApiResponse.<CategoryResponse>builder()
                .result(response)
>>>>>>> huy
                .build();
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    public ApiResponse<Category> updateCategory(@PathVariable String id, @RequestBody @Valid CategoryDto categoryDto) {
        Category category = categoryService.updateCategory(id, categoryDto);
        return ApiResponse.<Category>builder()
                .result(category)
=======
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable String id, @RequestBody @Valid CategoryDto categoryDto) {
        CategoryResponse response = categoryService.updateCategory(id, categoryDto);
        return ApiResponse.<CategoryResponse>builder()
                .result(response)
>>>>>>> huy
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
