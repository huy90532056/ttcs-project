package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.DiscountCreationRequest;
import com.shopee.ecommerce_web.dto.request.DiscountUpdateRequest;
import com.shopee.ecommerce_web.dto.response.DiscountResponse;
import com.shopee.ecommerce_web.entity.Discount;
import com.shopee.ecommerce_web.repository.DiscountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountService {

    DiscountRepository discountRepository;

    // Create a new Discount
    public DiscountResponse createDiscount(DiscountCreationRequest request) {
        Discount discount = Discount.builder()
                .discountCode(request.getDiscountCode())
                .percentageOff(request.getPercentageOff())
                .validFrom(request.getValidFrom())
                .validUntil(request.getValidUntil())
                .build();

        Discount savedDiscount = discountRepository.save(discount);

        return new DiscountResponse(
                savedDiscount.getDiscountId(),
                savedDiscount.getDiscountCode(),
                savedDiscount.getPercentageOff(),
                savedDiscount.getValidFrom(),
                savedDiscount.getValidUntil()
        );
    }

    // Get all Discounts
    public List<DiscountResponse> getAllDiscounts() {
        List<Discount> discounts = discountRepository.findAll();

        return discounts.stream()
                .map(discount -> new DiscountResponse(
                        discount.getDiscountId(),
                        discount.getDiscountCode(),
                        discount.getPercentageOff(),
                        discount.getValidFrom(),
                        discount.getValidUntil()
                ))
                .collect(Collectors.toList());
    }

    // Get a Discount by ID
    public DiscountResponse getDiscountById(Long discountId) {
        Optional<Discount> discountOpt = discountRepository.findById(discountId);

        if (discountOpt.isEmpty()) {
            throw new RuntimeException("Discount not found for ID: " + discountId);
        }

        Discount discount = discountOpt.get();
        return new DiscountResponse(
                discount.getDiscountId(),
                discount.getDiscountCode(),
                discount.getPercentageOff(),
                discount.getValidFrom(),
                discount.getValidUntil()
        );
    }

    // Update an existing Discount
    public DiscountResponse updateDiscount(Long discountId, DiscountUpdateRequest request) {
        Optional<Discount> discountOpt = discountRepository.findById(discountId);

        if (discountOpt.isEmpty()) {
            throw new RuntimeException("Discount not found for ID: " + discountId);
        }

        Discount discount = discountOpt.get();
        discount.setPercentageOff(request.getPercentageOff());
        discount.setValidFrom(request.getValidFrom());
        discount.setValidUntil(request.getValidUntil());

        Discount updatedDiscount = discountRepository.save(discount);

        return new DiscountResponse(
                updatedDiscount.getDiscountId(),
                updatedDiscount.getDiscountCode(),
                updatedDiscount.getPercentageOff(),
                updatedDiscount.getValidFrom(),
                updatedDiscount.getValidUntil()
        );
    }

    // Delete a Discount
    public void deleteDiscount(Long discountId) {
        Optional<Discount> discountOpt = discountRepository.findById(discountId);

        if (discountOpt.isEmpty()) {
            throw new RuntimeException("Discount not found for ID: " + discountId);
        }

        discountRepository.delete(discountOpt.get());
    }
}
