package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.InventoryCreationRequest;
import com.shopee.ecommerce_web.dto.response.InventoryResponse;
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

    // Create new Inventory
    public InventoryResponse createInventory(InventoryCreationRequest request) {
        // Tìm kiếm User từ ID trong request
        User user = userRepository.findById(request.getUserId())  // Giả sử ID của User được cung cấp trong request
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));  // Nếu không tìm thấy User, throw Exception

        // Tạo mới Inventory và thiết lập User
        Inventory inventory = new Inventory();
        inventory.setUser(user);

        // Lưu Inventory vào database
        inventory = inventoryRepository.save(inventory);

        // Lấy danh sách ProductInventory liên quan đến Inventory (giả sử Inventory có mối quan hệ với ProductInventory)
        List<ProductInventory> productInventories = productInventoryRepository.findByInventory(inventory);

        // Chuyển đổi Inventory thành InventoryResponse
        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(inventory.getInventoryId());
        response.setUserId(inventory.getUser().getId()); // Giả sử User có phương thức getUserId()
        response.setProductInventories(productInventories); // Thêm productInventories vào response

        return response;
    }

    // Get all Inventories
    public List<InventoryResponse> getInventories() {
        return inventoryRepository.findAll().stream()
                .map(inventory -> {
                    List<ProductInventory> productInventories = productInventoryRepository.findByInventory(inventory);
                    InventoryResponse response = new InventoryResponse();
                    response.setInventoryId(inventory.getInventoryId());
                    response.setUserId(inventory.getUser().getId()); // Assuming user has getUserId() method
                    response.setProductInventories(productInventories); // Thêm productInventories vào response
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
        response.setUserId(inventory.getUser().getId()); // Assuming user has getUserId() method
        response.setProductInventories(productInventories); // Thêm productInventories vào response
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
            response.setProductInventories(productInventories); // Thêm productInventories vào response
            return response;
        }).toList();
    }
}

