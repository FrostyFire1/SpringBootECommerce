package com.frosty.SpringBootECommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private Long sellerId;
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
