package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.ProductInventoryCreationRequest;
import com.shopee.ecommerce_web.dto.request.ProductInventoryUpdateRequest;
import com.shopee.ecommerce_web.dto.response.ProductInventoryResponse;
import com.shopee.ecommerce_web.entity.Inventory;
import com.shopee.ecommerce_web.entity.Product;
import com.shopee.ecommerce_web.entity.ProductInventory;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.InventoryRepository;
import com.shopee.ecommerce_web.repository.ProductInventoryRepository;
import com.shopee.ecommerce_web.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductInventoryService {

    ProductInventoryRepository productInventoryRepository;
    ProductRepository productRepository;
    InventoryRepository inventoryRepository;

    // Tạo mới ProductInventory
    public ProductInventoryResponse createProductInventory(ProductInventoryCreationRequest request) {
        // Tìm Product và Inventory từ ID
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Inventory inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_EXISTED));

        // Tạo ProductInventory
        ProductInventory productInventory = new ProductInventory();
        productInventory.setProduct(product);
        productInventory.setInventory(inventory);
        productInventory.setQuantity(request.getQuantity());

        // Lưu ProductInventory vào database
        productInventory = productInventoryRepository.save(productInventory);

        // Trả về response
        return new ProductInventoryResponse(
                productInventory.getProductInventoryId(),
                productInventory.getProduct().getProductId(),
                productInventory.getInventory().getInventoryId(),
                productInventory.getQuantity()
        );
    }

    // Lấy tất cả ProductInventory
    public List<ProductInventoryResponse> getAllProductInventories() {
        return productInventoryRepository.findAll().stream()
                .map(productInventory -> new ProductInventoryResponse(
                        productInventory.getProductInventoryId(),
                        productInventory.getProduct().getProductId(),
                        productInventory.getInventory().getInventoryId(),
                        productInventory.getQuantity()))
                .collect(Collectors.toList());
    }

    // Lấy ProductInventory theo ID
    public ProductInventoryResponse getProductInventoryById(Long productInventoryId) {
        ProductInventory productInventory = productInventoryRepository.findById(productInventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_INVENTORY_NOT_FOUND));

        return new ProductInventoryResponse(
                productInventory.getProductInventoryId(),
                productInventory.getProduct().getProductId(),
                productInventory.getInventory().getInventoryId(),
                productInventory.getQuantity()
        );
    }

    // Cập nhật ProductInventory
    public ProductInventoryResponse updateProductInventory(Long productInventoryId, ProductInventoryUpdateRequest request) {
        ProductInventory productInventory = productInventoryRepository.findById(productInventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_INVENTORY_NOT_FOUND));

        // Tìm Product và Inventory từ ID
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Inventory inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_EXISTED));

        // Cập nhật thông tin ProductInventory
        productInventory.setProduct(product);
        productInventory.setInventory(inventory);
        productInventory.setQuantity(request.getQuantity());

        // Lưu thay đổi vào database
        productInventory = productInventoryRepository.save(productInventory);

        // Trả về response
        return new ProductInventoryResponse(
                productInventory.getProductInventoryId(),
                productInventory.getProduct().getProductId(),
                productInventory.getInventory().getInventoryId(),
                productInventory.getQuantity()
        );
    }

    // Xóa ProductInventory
    public void deleteProductInventory(Long productInventoryId) {
        productInventoryRepository.deleteById(productInventoryId);
    }
}
