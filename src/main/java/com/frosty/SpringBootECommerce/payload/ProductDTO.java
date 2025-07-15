package com.frosty.SpringBootECommerce.payload;

import com.frosty.SpringBootECommerce.model.Category;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String description;
    private Double discount;
    private String image;
    private Double price;
    private String productName;
    private Integer quantity;
    private Double specialPrice;
    private Long sellerId;
    private Category category;
}
