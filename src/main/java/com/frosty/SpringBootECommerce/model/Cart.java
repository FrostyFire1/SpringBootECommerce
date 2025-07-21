package com.frosty.SpringBootECommerce.model;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    private Double totalPrice;

    @OneToMany(
            mappedBy = "cart",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    Set<CartItem> cartItems = new LinkedHashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Cart(Double totalPrice, User user) {
        this.totalPrice = totalPrice;
        this.user = user;
    }
}
