package com.shopee.ecommerce_web.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @JsonBackReference
    @NotNull(message = "Product Variant is mandatory")
    @ManyToOne
    @JoinColumn(name = "variant_id", referencedColumnName = "variantId")  // Liên kết với ProductVariant
    private ProductVariant productVariant;  // Thay "product" bằng "productVariant"

    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;

    @NotNull(message = "Total price is mandatory")
    private Double totalPrice;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Order order;
}
