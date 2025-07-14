package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.exception.APIException;
import com.frosty.SpringBootECommerce.exception.ResourceNotFoundException;
import com.frosty.SpringBootECommerce.model.Category;
import com.frosty.SpringBootECommerce.payload.CategoryDTO;
import com.frosty.SpringBootECommerce.payload.CategoryResponse;
import com.frosty.SpringBootECommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceProvider implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryResponse getAllCategories(Integer page, Integer pageSize) {
        Pageable pageDetails =  PageRequest.of(page, pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<CategoryDTO> categories = categoryPage
                .getContent()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        if(categories.isEmpty()){
            throw new APIException("No categories found");
        }
        return CategoryResponse.builder()
                .content(categories)
                .page(page)
                .pageSize(pageSize)
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .lastPage(categoryPage.isLast())
                .build();
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if(existingCategory != null) {
            throw new APIException("Category with the name " + categoryDTO.getCategoryName() + " already exists");
        }
        Category savedCategory = categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category toRemove = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId", categoryId));
        categoryRepository.delete(toRemove);
        return modelMapper.map(toRemove, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId", categoryId));
        categoryDTO.setCategoryId(categoryId);
        Category category = modelMapper.map(categoryDTO,  Category.class);
        return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
    }
}
