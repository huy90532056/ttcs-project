package com.shopee.ecommerce_web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", updatable = false, nullable = false)
    private String categoryId;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;

    @Column(name = "category_icon")
    private String categoryIcon;

    @Column(name = "category_image_path")
    private String categoryImagePath;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<Product> products;
}
