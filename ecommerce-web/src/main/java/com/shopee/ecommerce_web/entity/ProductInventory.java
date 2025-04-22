package com.shopee.ecommerce_web.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductInventory extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productInventoryId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "inventory_id", referencedColumnName = "inventoryId")
    private Inventory inventory;

    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;

}