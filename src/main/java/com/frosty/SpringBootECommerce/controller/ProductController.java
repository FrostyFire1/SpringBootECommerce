package com.frosty.SpringBootECommerce.controller;

import com.frosty.SpringBootECommerce.configuration.AppConstants;
import com.frosty.SpringBootECommerce.payload.ProductDTO;
import com.frosty.SpringBootECommerce.payload.ProductResponse;
import com.frosty.SpringBootECommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                          @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                          @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_PRODUCTS, required = false) String sortBy,
                                                          @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder));
    }
    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsInCategory(@PathVariable Long categoryId,
                                                          @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                          @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                          @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_PRODUCTS, required = false) String sortBy,
                                                          @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(productService.getProductsInCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder));
    }
}
