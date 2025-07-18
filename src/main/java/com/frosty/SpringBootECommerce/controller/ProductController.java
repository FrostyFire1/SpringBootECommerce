package com.frosty.SpringBootECommerce.controller;

import com.frosty.SpringBootECommerce.configuration.AppConstants;
import com.frosty.SpringBootECommerce.payload.ContentResponse;
import com.frosty.SpringBootECommerce.payload.ProductDTO;
import com.frosty.SpringBootECommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid ProductDTO productDTO,
                                                 @PathVariable("categoryId") Long categoryId) {
        return new ResponseEntity<>(productService.addProduct(productDTO, categoryId), HttpStatus.CREATED);
    }
    @GetMapping("/public/products")
    public ResponseEntity<ContentResponse<ProductDTO>> getAllProducts(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                                      @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                      @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_PRODUCTS, required = false) String sortBy,
                                                                      @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder));
    }
    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ContentResponse<ProductDTO>> getProductsInCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_PRODUCTS, required = false) String sortBy,
                                                                 @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(productService.getProductsInCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder));
    }
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ContentResponse<ProductDTO>> getProductsByKeyword(@PathVariable String keyword,
                                                                 @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_PRODUCTS, required = false) String sortBy,
                                                                 @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(productService.getProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder));
    }
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId,
                                                    @RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productId, productDTO));
    }
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> removeProduct(@PathVariable Long productId){
        return ResponseEntity.ok(productService.removeProduct(productId));
    }
    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(productService.updateProductImage(productId, image));
    }
}
