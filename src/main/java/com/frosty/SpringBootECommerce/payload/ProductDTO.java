package com.frosty.SpringBootECommerce.payload;

import com.frosty.SpringBootECommerce.configuration.AppConstants;
import com.frosty.SpringBootECommerce.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;

    private String name;

    private String description;

    private Integer quantity;

    private Double price;

    private Double discount = 0.0;
    private Double specialPrice;
    private Long sellerId;
    private String image = AppConstants.DEFAULT_IMAGE_NAME;
    private Category category;
}
