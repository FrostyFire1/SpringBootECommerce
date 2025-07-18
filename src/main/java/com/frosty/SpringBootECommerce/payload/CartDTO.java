package com.frosty.SpringBootECommerce.payload;

import com.frosty.SpringBootECommerce.model.Cart;
import com.frosty.SpringBootECommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Double totalPrice = 0.0;
    private Set<ProductDTO> products = new HashSet<>();
}
