package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.ContentResponse;
import com.frosty.SpringBootECommerce.payload.ProductDTO;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ContentResponse<ProductDTO> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ContentResponse<ProductDTO> getProductsInCategory(
            Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ContentResponse<ProductDTO> getProductsByKeyword(
            String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO removeProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
