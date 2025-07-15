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
    private String description;
    private Double discount;
    private String image;
    private Double price;
    private String productName;
    private Integer quantity;
    private Double specialPrice;
    private Long sellerId;
    @ManyToOne
    private Category category;
}
