package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.InventoryCreationRequest;
import com.shopee.ecommerce_web.dto.response.InventoryResponse;
import com.shopee.ecommerce_web.entity.FileS3;
import com.shopee.ecommerce_web.entity.Inventory;
import com.shopee.ecommerce_web.entity.ProductInventory;
import com.shopee.ecommerce_web.entity.User;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.InventoryRepository;
import com.shopee.ecommerce_web.repository.ProductInventoryRepository;
import com.shopee.ecommerce_web.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryService {

    InventoryRepository inventoryRepository;
    UserRepository userRepository;  // Thêm UserRepository để tìm User từ ID
    ProductInventoryRepository productInventoryRepository;  // Giả sử có repository cho ProductInventory

    FileS3Service fileS3Service;  // Giả sử có service để upload file lên S3
    // Create new Inventory
    public InventoryResponse createInventory(InventoryCreationRequest request) {
        // Tìm kiếm User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Upload ảnh nếu có
        String imageUrl = null;
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            String uniqueName = "inventory_" + System.currentTimeMillis();
            FileS3 uploadedFile = fileS3Service.uploadFile(request.getImageFile(), uniqueName);
            imageUrl = uploadedFile.getFileUrl();
        }

        // Tạo mới Inventory
        Inventory inventory = new Inventory();
        inventory.setUser(user);
        inventory.setInventoryImage(imageUrl); // lưu URL ảnh

        // Lưu vào DB
        inventory = inventoryRepository.save(inventory);

        // Lấy danh sách ProductInventory liên quan
        List<ProductInventory> productInventories = productInventoryRepository.findByInventory(inventory);

        // Trả response
        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(inventory.getInventoryId());
        response.setUserId(user.getId());
        response.setInventoryImagePath(imageUrl); // gán URL ảnh vào response
        response.setProductInventories(productInventories);

        return response;
    }


    // Get all Inventories
    public List<InventoryResponse> getInventories() {
        return inventoryRepository.findAll().stream()
                .map(inventory -> {
                    List<ProductInventory> productInventories = productInventoryRepository.findByInventory(inventory);
                    InventoryResponse response = new InventoryResponse();
                    response.setInventoryId(inventory.getInventoryId());
                    response.setUserId(inventory.getUser().getId());
                    response.setInventoryImagePath(inventory.getInventoryImage()); // Thêm dòng này
                    response.setProductInventories(productInventories);
                    return response;
                })
                .toList();
    }


    // Get Inventory by ID
    public InventoryResponse getInventory(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_EXISTED));

        List<ProductInventory> productInventories = productInventoryRepository.findByInventory(inventory);

        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(inventory.getInventoryId());
        response.setUserId(inventory.getUser().getId());
        response.setInventoryImagePath(inventory.getInventoryImage()); // Thêm dòng này
        response.setProductInventories(productInventories);

        return response;
    }


    // Delete Inventory
    public void deleteInventory(Long inventoryId) {
        if (!inventoryRepository.existsById(inventoryId)) {
            throw new AppException(ErrorCode.INVENTORY_NOT_EXISTED);
        }

        inventoryRepository.deleteById(inventoryId);
    }

    // Get Inventories by UserId
    public List<InventoryResponse> getInventoriesByUserId(String userId) {
        List<Inventory> inventories = inventoryRepository.findAllByUser_Id(userId);

        return inventories.stream().map(inventory -> {
            List<ProductInventory> productInventories = productInventoryRepository.findByInventory(inventory);
            InventoryResponse response = new InventoryResponse();
            response.setInventoryId(inventory.getInventoryId());
            response.setUserId(inventory.getUser().getId());
            response.setInventoryImagePath(inventory.getInventoryImage()); // Thêm dòng này
            response.setProductInventories(productInventories);
            return response;
        }).toList();
    }

    // Get a single Inventory ID by Product ID
    public Long getInventoryIdByProductId(Long productId) {
        List<ProductInventory> productInventories = productInventoryRepository.findByProduct_ProductId(productId);

        if (productInventories.isEmpty()) {
            throw new AppException(ErrorCode.INVENTORY_NOT_EXISTED); // hoặc PRODUCT_NOT_EXISTED nếu bạn có
        }

        // Trả về inventoryId đầu tiên tìm được
        return productInventories.get(0).getInventory().getInventoryId();
    }


}

