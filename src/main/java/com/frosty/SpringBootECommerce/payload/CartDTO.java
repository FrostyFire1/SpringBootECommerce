package com.frosty.SpringBootECommerce.payload;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Double totalPrice = 0.0;
    private Set<ProductDTO> products = new HashSet<>();
}
