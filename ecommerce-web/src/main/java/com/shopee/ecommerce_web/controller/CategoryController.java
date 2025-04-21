package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.CategoryDto;
import com.shopee.ecommerce_web.entity.Category;
import com.shopee.ecommerce_web.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<Category> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        Category category = categoryService.createCategory(categoryDto);
        return ApiResponse.<Category>builder()
                .result(category)
                .build();
    }

    @GetMapping
    public ApiResponse<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ApiResponse.<List<Category>>builder()
                .result(categories)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Category> getCategoryById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id);
        return ApiResponse.<Category>builder()
                .result(category)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Category> updateCategory(@PathVariable String id, @RequestBody @Valid CategoryDto categoryDto) {
        Category category = categoryService.updateCategory(id, categoryDto);
        return ApiResponse.<Category>builder()
                .result(category)
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
