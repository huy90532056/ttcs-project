package com.shopee.ecommerce_web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user_discount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDiscount extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private Discount discount;

    private Boolean isUsed = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date usedAt;

}
