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
public class CartItem extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "variant_id", referencedColumnName = "variantId")
    private ProductVariant productVariant;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    private Cart cart;

}