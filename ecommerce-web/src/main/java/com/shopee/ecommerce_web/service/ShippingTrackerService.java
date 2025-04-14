package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.ShippingTrackerCreationRequest;
import com.shopee.ecommerce_web.dto.request.ShippingTrackerUpdateRequest;
import com.shopee.ecommerce_web.dto.response.ShippingTrackerResponse;
import com.shopee.ecommerce_web.entity.Order;
import com.shopee.ecommerce_web.entity.ShippingStatus;
import com.shopee.ecommerce_web.entity.ShippingTracker;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.OrderRepository;
import com.shopee.ecommerce_web.repository.ShippingTrackerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShippingTrackerService {

    private final ShippingTrackerRepository shippingTrackerRepository;
    private final OrderRepository orderRepository;

    // Tạo một Shipping Tracker mới
    public ShippingTrackerResponse createShippingTracker(ShippingTrackerCreationRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        ShippingTracker shippingTracker = ShippingTracker.builder()
                .order(order)
                .trackingNumber(request.getTrackingNumber())
                .shippingMethod(request.getShippingMethod())
                .carrier(request.getCarrier())
                .status(ShippingStatus.valueOf(request.getStatus()))
                .build();

        shippingTracker = shippingTrackerRepository.save(shippingTracker);

        return ShippingTrackerResponse.builder()
                .trackingId(shippingTracker.getTrackingId())
                .orderId(shippingTracker.getOrder().getOrderId())
                .trackingNumber(shippingTracker.getTrackingNumber())
                .shippingMethod(shippingTracker.getShippingMethod())
                .carrier(shippingTracker.getCarrier())
                .status(shippingTracker.getStatus())
                .build();
    }

    // Lấy tất cả các Shipping Tracker
    public List<ShippingTrackerResponse> getShippingTrackers() {
        return shippingTrackerRepository.findAll().stream()
                .map(shippingTracker -> ShippingTrackerResponse.builder()
                        .trackingId(shippingTracker.getTrackingId())
                        .orderId(shippingTracker.getOrder().getOrderId())
                        .trackingNumber(shippingTracker.getTrackingNumber())
                        .shippingMethod(shippingTracker.getShippingMethod())
                        .carrier(shippingTracker.getCarrier())
                        .status(shippingTracker.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    // Lấy Shipping Tracker theo ID
    public ShippingTrackerResponse getShippingTracker(Long trackingId) {
        ShippingTracker shippingTracker = shippingTrackerRepository.findById(trackingId)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_TRACKER_NOT_FOUND));

        return ShippingTrackerResponse.builder()
                .trackingId(shippingTracker.getTrackingId())
                .orderId(shippingTracker.getOrder().getOrderId())
                .trackingNumber(shippingTracker.getTrackingNumber())
                .shippingMethod(shippingTracker.getShippingMethod())
                .carrier(shippingTracker.getCarrier())
                .status(shippingTracker.getStatus())
                .build();
    }

    // Cập nhật Shipping Tracker
    public ShippingTrackerResponse updateShippingTracker(Long trackingId, ShippingTrackerUpdateRequest request) {
        ShippingTracker shippingTracker = shippingTrackerRepository.findById(trackingId)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_TRACKER_NOT_FOUND));

        if (request.getTrackingNumber() != null) {
            shippingTracker.setTrackingNumber(request.getTrackingNumber());
        }
        if (request.getShippingMethod() != null) {
            shippingTracker.setShippingMethod(request.getShippingMethod());
        }
        if (request.getCarrier() != null) {
            shippingTracker.setCarrier(request.getCarrier());
        }
        if (request.getStatus() != null) {
            shippingTracker.setStatus(ShippingStatus.valueOf(request.getStatus()));
        }

        shippingTracker = shippingTrackerRepository.save(shippingTracker);

        return ShippingTrackerResponse.builder()
                .trackingId(shippingTracker.getTrackingId())
                .orderId(shippingTracker.getOrder().getOrderId())
                .trackingNumber(shippingTracker.getTrackingNumber())
                .shippingMethod(shippingTracker.getShippingMethod())
                .carrier(shippingTracker.getCarrier())
                .status(shippingTracker.getStatus())
                .build();
    }

    // Xóa Shipping Tracker
    public void deleteShippingTracker(Long trackingId) {
        ShippingTracker shippingTracker = shippingTrackerRepository.findById(trackingId)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPING_TRACKER_NOT_FOUND));

        shippingTrackerRepository.delete(shippingTracker);
    }
}
