package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.ProductDTO;
import com.frosty.SpringBootECommerce.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
