package com.frosty.SpringBootECommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cart_items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    private Double discount;
    private Double productPrice;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "product_id")
    private Product product;
}
