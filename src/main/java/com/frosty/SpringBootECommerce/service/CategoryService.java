package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.CategoryDTO;
import com.frosty.SpringBootECommerce.payload.CategoryResponse;


public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
