package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.configuration.AppConstants;
import com.frosty.SpringBootECommerce.exception.APIException;
import com.frosty.SpringBootECommerce.exception.ResourceNotFoundException;
import com.frosty.SpringBootECommerce.model.Category;
import com.frosty.SpringBootECommerce.model.Product;
import com.frosty.SpringBootECommerce.payload.ContentResponse;
import com.frosty.SpringBootECommerce.payload.ProductDTO;
import com.frosty.SpringBootECommerce.payload.ProductResponse;
import com.frosty.SpringBootECommerce.repository.CategoryRepository;
import com.frosty.SpringBootECommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceProvider implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String imagePath;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        if(category.getProducts()
                .stream()
                .anyMatch(p -> p.getName().equals(productDTO.getName()))) {
            throw new APIException("Product already exists");
        }

        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setSpecialPrice(product.getPrice() * (1 -  (product.getDiscount()/100)));

        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ContentResponse<ProductDTO> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Page<Product> productPage = GlobalServiceHelper.getAllItems(pageNumber, pageSize, sortBy, sortOrder, productRepository);
        List<ProductDTO> products = GlobalServiceHelper.getDTOContent(productPage, ProductDTO.class, modelMapper, "No Products Found");
        return ContentResponse.<ProductDTO>builder()
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
        Page<Product> productPage = GlobalServiceHelper.getAllItemsBy(pageNumber, pageSize, sortBy, sortOrder, productRepository::findByNameLikeIgnoreCase, '%' + keyword + '%');
        List<ProductDTO> products = GlobalServiceHelper.getDTOContent(productPage,
                ProductDTO.class,
                modelMapper,
                "No Products Found");
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
        Product saved = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        Category category = saved.getCategory();
        if(productDTO.getCategory() != null){
            category = categoryRepository
                    .findById(productDTO.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", productDTO.getCategory().getId()));
        }

        Product product = modelMapper.map(productDTO, Product.class);
        product.setId(productId);
        product.setSpecialPrice(product.getPrice() * (1 -  (product.getDiscount()/100)));
        product.setCategory(category);

        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO removeProduct(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        String fileName = fileService.uploadFile(imagePath, image);
        product.setImage(fileName);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }
}
