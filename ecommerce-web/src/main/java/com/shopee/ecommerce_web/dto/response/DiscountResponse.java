package com.shopee.ecommerce_web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse {

    private Long discountId; // ID of the discount
    private String discountCode; // Discount code
    private Double percentageOff; // Percentage discount
    private Date validFrom; // Start date of the discount
    private Date validUntil; // End date of the discount
}
