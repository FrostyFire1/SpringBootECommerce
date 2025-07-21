package com.frosty.SpringBootECommerce.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
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
  @Column(name = "product_id")
  private Long id;

  private String name;
  private String description;
  private Integer quantity;
  private Double price;
  private Double discount;
  private Double specialPrice;
  private String image;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "seller_id")
  private User seller;

  @OneToMany(
      mappedBy = "product",
      cascade = {CascadeType.MERGE, CascadeType.PERSIST},
      fetch = FetchType.EAGER)
  private Set<CartItem> cartItems = new HashSet<>();
}
