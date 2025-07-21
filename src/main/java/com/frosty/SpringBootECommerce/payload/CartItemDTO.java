package com.frosty.SpringBootECommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
  private Long id;
  private Double discount;
  private Double productPrice;
  private Integer quantity;

  private CartDTO cart;
  private ProductDTO product;
}
