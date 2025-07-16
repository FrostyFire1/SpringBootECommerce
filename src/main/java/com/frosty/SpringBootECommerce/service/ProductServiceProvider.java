package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.exception.APIException;
import com.frosty.SpringBootECommerce.exception.ResourceNotFoundException;
import com.frosty.SpringBootECommerce.model.Category;
import com.frosty.SpringBootECommerce.model.Product;
import com.frosty.SpringBootECommerce.payload.ProductDTO;
import com.frosty.SpringBootECommerce.payload.ProductResponse;
import com.frosty.SpringBootECommerce.repository.CategoryRepository;
import com.frosty.SpringBootECommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceProvider implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setSpecialPrice(product.getPrice() * (1 -  (product.getDiscount()/100)));

        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Page<Product> productPage = GlobalServiceHelper.getAllItems(pageNumber, pageSize, sortBy, sortOrder, productRepository);
        List<ProductDTO> products = GlobalServiceHelper.getDTOContent(productPage, ProductDTO.class, modelMapper, "No Products Found");
        return ProductResponse.builder()
                .content(products)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .lastPage(productPage.isLast())
                .build();
    }

    @Override
    public ProductResponse getProductsInCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Page<Product> productPage = GlobalServiceHelper.getAllItemsBy(pageNumber, pageSize, sortBy, sortOrder, productRepository::findByCategory_Id, categoryId);
        List<ProductDTO> products = GlobalServiceHelper.getDTOContent(productPage, ProductDTO.class, modelMapper, "No Products Found");

        return ProductResponse.builder()
                .content(products)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .lastPage(productPage.isLast())
                .build();
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Page<Product> productPage = GlobalServiceHelper.getAllItems(pageNumber, pageSize, sortBy, sortOrder, productRepository);
        List<ProductDTO> products = GlobalServiceHelper.getDTOContent(productPage,
                ProductDTO.class,
                modelMapper,
                "No Products Found",
                product -> product.getName().toLowerCase().contains(keyword.toLowerCase()));

        return ProductResponse.builder()
                .content(products)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .lastPage(productPage.isLast())
                .build();
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        return null;
    }
}
