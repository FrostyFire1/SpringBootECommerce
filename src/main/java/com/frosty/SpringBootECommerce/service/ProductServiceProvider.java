package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.exception.APIException;
import com.frosty.SpringBootECommerce.exception.ResourceNotFoundException;
import com.frosty.SpringBootECommerce.model.Category;
import com.frosty.SpringBootECommerce.model.Product;
import com.frosty.SpringBootECommerce.payload.CategoryDTO;
import com.frosty.SpringBootECommerce.payload.ProductDTO;
import com.frosty.SpringBootECommerce.payload.ProductResponse;
import com.frosty.SpringBootECommerce.repository.CategoryRepository;
import com.frosty.SpringBootECommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Sort sortDetails = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails =  PageRequest.of(pageNumber, pageSize, sortDetails);
        Page<Product> productPage = productRepository.findAll(pageDetails);
        List<ProductDTO> products = productPage
                .getContent()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        if(products.isEmpty()){
            throw new APIException("No products found");
        }
        return ProductResponse.builder()
                .content(products)
                .page(pageNumber)
                .pageSize(pageSize)
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .lastPage(productPage.isLast())
                .build();
    }
}
