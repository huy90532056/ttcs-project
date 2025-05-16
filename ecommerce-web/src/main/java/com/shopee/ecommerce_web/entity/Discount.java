package com.shopee.ecommerce_web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discount")
public class Discount extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;
    @NotBlank(message = "Discount code is mandatory")
    private String discountCode;

    @NotNull(message = "Percentage off is mandatory")
    private Double percentageOff;

    @NotNull(message = "Valid from date is mandatory")
    @Temporal(TemporalType.DATE)
    private Date validFrom;

    @NotNull(message = "Valid until date is mandatory")
    @Temporal(TemporalType.DATE)
    private Date validUntil;
}