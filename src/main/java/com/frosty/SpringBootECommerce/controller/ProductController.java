package com.frosty.SpringBootECommerce.controller;

import com.frosty.SpringBootECommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
}
