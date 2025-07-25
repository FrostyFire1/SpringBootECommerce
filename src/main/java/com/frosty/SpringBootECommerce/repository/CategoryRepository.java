package com.frosty.SpringBootECommerce.repository;

import com.frosty.SpringBootECommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
