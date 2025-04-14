package com.shopee.ecommerce_web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipping")
public class ShippingTracker extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackingId;

    private String trackingNumber;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Order order;
    private String shippingMethod;

    private String carrier;

    @NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    private ShippingStatus status; // Trạng thái (ví dụ: "In Transit", "Delivered")
}