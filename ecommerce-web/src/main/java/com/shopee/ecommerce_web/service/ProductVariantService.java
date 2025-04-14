package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.ProductVariantRequest;
import com.shopee.ecommerce_web.dto.response.ProductVariantResponse;
import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.entity.ProductVariant;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.ProductRepository;
import com.shopee.ecommerce_web.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;

    // Tạo mới ProductVariant
    public ProductVariantResponse createProductVariant(ProductVariantRequest request) {
        // Kiểm tra xem Product có tồn tại không
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Tạo mới ProductVariant và thiết lập các giá trị từ request
        ProductVariant productVariant = ProductVariant.builder()
                .variantName(request.getVariantName())
                .variantValue(request.getVariantValue())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .product(product) // Thiết lập quan hệ ManyToOne với Product
                .build();

        // Lưu vào database
        productVariant = productVariantRepository.save(productVariant);

        // Trả về đối tượng ProductVariantResponse
        return new ProductVariantResponse(
                productVariant.getVariantId(),
                productVariant.getVariantName(),
                productVariant.getVariantValue(),
                productVariant.getPrice(),
                productVariant.getStockQuantity(),
                productVariant.getProduct().getProductId()
        );
    }

    // Lấy ProductVariant theo ID
    public ProductVariantResponse getProductVariantById(UUID variantId) {
        ProductVariant productVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        // Chuyển đổi từ ProductVariant sang ProductVariantResponse
        return new ProductVariantResponse(
                productVariant.getVariantId(),
                productVariant.getVariantName(),
                productVariant.getVariantValue(),
                productVariant.getPrice(),
                productVariant.getStockQuantity(),
                productVariant.getProduct().getProductId()
        );
    }

    // Cập nhật ProductVariant
    public ProductVariantResponse updateProductVariant(UUID variantId, ProductVariantRequest request) {
        ProductVariant productVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        // Cập nhật thông tin ProductVariant
        productVariant.setVariantName(request.getVariantName());
        productVariant.setVariantValue(request.getVariantValue());
        productVariant.setPrice(request.getPrice());
        productVariant.setStockQuantity(request.getStockQuantity());

        // Lưu thay đổi
        productVariant = productVariantRepository.save(productVariant);

        // Trả về đối tượng ProductVariantResponse đã được cập nhật
        return new ProductVariantResponse(
                productVariant.getVariantId(),
                productVariant.getVariantName(),
                productVariant.getVariantValue(),
                productVariant.getPrice(),
                productVariant.getStockQuantity(),
                productVariant.getProduct().getProductId()
        );
    }

    // Thêm phương thức này trong ProductVariantService
    public List<ProductVariantResponse> getAllProductVariants() {
        // Lấy tất cả các ProductVariant từ repository
        List<ProductVariant> productVariants = productVariantRepository.findAll();

        // Chuyển đổi từ ProductVariant sang ProductVariantResponse
        return productVariants.stream().map(productVariant -> new ProductVariantResponse(
                productVariant.getVariantId(),
                productVariant.getVariantName(),
                productVariant.getVariantValue(),
                productVariant.getPrice(),
                productVariant.getStockQuantity(),
                productVariant.getProduct().getProductId()
        )).toList();
    }


    // Xóa ProductVariant
    public void deleteProductVariant(UUID variantId) {
        ProductVariant productVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        // Xóa ProductVariant khỏi database
        productVariantRepository.delete(productVariant);
    }
}
