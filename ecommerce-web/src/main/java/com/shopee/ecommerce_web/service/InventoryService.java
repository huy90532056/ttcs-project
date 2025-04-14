package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.InventoryCreationRequest;
import com.shopee.ecommerce_web.dto.response.InventoryResponse;
import com.shopee.ecommerce_web.entity.Inventory;
import com.shopee.ecommerce_web.entity.User;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.InventoryRepository;
import com.shopee.ecommerce_web.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryService {

    InventoryRepository inventoryRepository;
    UserRepository userRepository;  // Thêm UserRepository để tìm User từ ID

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

        // Chuyển đổi Inventory thành InventoryResponse
        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(inventory.getInventoryId());
        response.setUserId(inventory.getUser().getId()); // Giả sử User có phương thức getUserId()

        return response;
    }

    // Get all Inventories
    public List<InventoryResponse> getInventories() {
        return inventoryRepository.findAll().stream()
                .map(inventory -> {
                    InventoryResponse response = new InventoryResponse();
                    response.setInventoryId(inventory.getInventoryId());
                    response.setUserId(inventory.getUser().getId()); // Assuming user has getUserId() method
                    return response;
                })
                .toList();
    }

    // Get Inventory by ID
    public InventoryResponse getInventory(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_EXISTED));

        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(inventory.getInventoryId());
        response.setUserId(inventory.getUser().getId()); // Assuming user has getUserId() method
        return response;
    }

    // Update Inventory
    public InventoryResponse updateInventory(Long inventoryId, InventoryCreationRequest request) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_EXISTED));

        User user = userRepository.findById(request.getUserId())  // Giả sử ID của User được cung cấp trong request
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));  // Nếu không tìm thấy User, throw Exception

        // Update fields of Inventory
        inventory.setUser(user); // Assuming User is passed in the request

        // Save the updated Inventory
        inventory = inventoryRepository.save(inventory);

        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(inventory.getInventoryId());
        response.setUserId(inventory.getUser().getId()); // Assuming user has getUserId() method
        return response;
    }

    // Delete Inventory
    public void deleteInventory(Long inventoryId) {
        if (!inventoryRepository.existsById(inventoryId)) {
            throw new AppException(ErrorCode.INVENTORY_NOT_EXISTED);
        }

        inventoryRepository.deleteById(inventoryId);
    }
}
