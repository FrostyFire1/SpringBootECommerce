package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.payload.CategoryDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;

public interface CategoryService {
    ContentResponse<CategoryDTO> getAllCategories(
            Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
