package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
