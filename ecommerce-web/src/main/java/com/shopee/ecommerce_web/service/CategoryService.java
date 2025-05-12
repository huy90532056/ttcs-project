package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.CategoryDto;
import com.shopee.ecommerce_web.dto.response.CategoryResponse;
import com.shopee.ecommerce_web.entity.Category;
import com.shopee.ecommerce_web.entity.FileS3;
import com.shopee.ecommerce_web.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final FileS3Service fileS3Service;

    public CategoryResponse createCategory(CategoryDto categoryDto, MultipartFile categoryImage) {
        long imageCount = categoryRepository.count(); // Hoặc bạn có thể dùng một cách khác để đếm số ảnh

        // Tạo tên file với số tự động
        String fileName = "Category Image " + (imageCount + 1);

        // Upload ảnh lên S3 và lấy URL của file đã upload
        FileS3 uploadedFile = fileS3Service.uploadFile(categoryImage, fileName);

        // Tạo đối tượng Category từ CategoryDto
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryImagePath(uploadedFile.getFileUrl()); // Set đường dẫn ảnh đã upload
        category.setActive(categoryDto.getActive() != null ? categoryDto.getActive() : true);

        // Lưu category vào cơ sở dữ liệu
        Category saved = categoryRepository.save(category);

        // Trả về thông tin category đã lưu
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
                .categoryImagePath(category.getCategoryImagePath())
                .active(category.getActive())
                .build();
    }
}
