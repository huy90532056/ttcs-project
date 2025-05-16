package com.shopee.ecommerce_web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountUpdateRequest {

    private Double percentageOff;

    private Date validFrom;

    private Date validUntil;

}
