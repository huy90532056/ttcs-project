package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.DiscountCreationRequest;
import com.shopee.ecommerce_web.dto.request.DiscountUpdateRequest;
import com.shopee.ecommerce_web.dto.response.DiscountResponse;
import com.shopee.ecommerce_web.entity.Discount;
import com.shopee.ecommerce_web.entity.User;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.DiscountRepository;
import com.shopee.ecommerce_web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;

    public DiscountResponse createDiscount(DiscountCreationRequest request) {
        Discount discount = Discount.builder()
                .discountCode(request.getDiscountCode())
                .percentageOff(request.getPercentageOff())
                .validFrom(request.getValidFrom())
                .validUntil(request.getValidUntil())
                .build();

        Discount saved = discountRepository.save(discount);
        return convertToResponse(saved);
    }

    public List<DiscountResponse> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public DiscountResponse getDiscountById(Long discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));
        return convertToResponse(discount);
    }

    public DiscountResponse updateDiscount(Long discountId, DiscountUpdateRequest request) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_FOUND));

        discount.setPercentageOff(request.getPercentageOff());
        discount.setValidFrom(request.getValidFrom());
        discount.setValidUntil(request.getValidUntil());

        Discount updated = discountRepository.save(discount);
        return convertToResponse(updated);
    }

    public void deleteDiscount(Long discountId) {
        if (!discountRepository.existsById(discountId)) {
            throw new AppException(ErrorCode.DISCOUNT_NOT_FOUND);
        }
        discountRepository.deleteById(discountId);
    }

    private DiscountResponse convertToResponse(Discount discount) {
        return DiscountResponse.builder()
                .discountId(discount.getDiscountId())
                .discountCode(discount.getDiscountCode())
                .percentageOff(discount.getPercentageOff())
                .validFrom(discount.getValidFrom())
                .validUntil(discount.getValidUntil())
                .build();
    }
}
