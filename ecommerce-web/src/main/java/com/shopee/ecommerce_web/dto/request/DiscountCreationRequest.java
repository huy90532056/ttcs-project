package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCreationRequest {

    private String discountCode;

    private Double percentageOff;

    private Date validFrom;

    private Date validUntil;

}
