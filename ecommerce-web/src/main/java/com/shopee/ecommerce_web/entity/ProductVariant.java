package com.shopee.ecommerce_web.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_variant")
public class ProductVariant extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID variantId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "variant_name", nullable = false)
    private String variantName;

    @Column(name = "variant_value", nullable = false)
    private String variantValue;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    @OneToMany(mappedBy = "productVariant")
    private List<CartItem> cartItems;
    @JsonManagedReference
    @OneToMany(mappedBy = "productVariant")
    private List<OrderItem> orderItems;
}