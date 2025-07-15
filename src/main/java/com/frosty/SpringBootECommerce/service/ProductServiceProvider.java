package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceProvider implements ProductService {
    @Autowired
    private ProductRepository productRepository;
}
