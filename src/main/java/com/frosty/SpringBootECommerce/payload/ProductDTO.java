package com.frosty.SpringBootECommerce.payload;

import com.frosty.SpringBootECommerce.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Integer quantity;
    @NotNull
    private Double price;
    private Double discount = 0.0;
    private Double specialPrice;
    private Long sellerId;
    private String image;
    private Category category;
}
