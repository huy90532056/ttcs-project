package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.CategoryDto;
import com.shopee.ecommerce_web.dto.response.CategoryResponse;
import com.shopee.ecommerce_web.entity.Category;
import com.shopee.ecommerce_web.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryIcon(categoryDto.getCategoryIcon());
        category.setCategoryImagePath(categoryDto.getCategoryImagePath());
        category.setActive(categoryDto.getActive() != null ? categoryDto.getActive() : true);
        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found!"));
        return toResponse(category);
    }

    public CategoryResponse updateCategory(String id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found!"));

        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryIcon(categoryDto.getCategoryIcon());
        category.setCategoryImagePath(categoryDto.getCategoryImagePath());
        category.setActive(categoryDto.getActive());

        Category updated = categoryRepository.save(category);
        return toResponse(updated);
    }

    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found!"));
        categoryRepository.delete(category);
    }

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryDescription(category.getCategoryDescription())
                .categoryIcon(category.getCategoryIcon())
                .categoryImagePath(category.getCategoryImagePath())
                .active(category.getActive())
                .build();
    }
}
