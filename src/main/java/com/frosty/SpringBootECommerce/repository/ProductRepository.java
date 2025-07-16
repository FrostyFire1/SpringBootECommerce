package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory_Id(Long categoryId,
                                    Pageable pageable);
    Page<Product> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
