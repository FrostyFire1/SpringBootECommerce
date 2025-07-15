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
import org.springframework.data.domain.Sort;
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
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortDetails = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails =  PageRequest.of(pageNumber, pageSize, sortDetails);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<CategoryDTO> categories = categoryPage
                .getContent()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        if(categories.isEmpty()){
            throw new APIException("No categories found");
        }

        return CategoryResponse.builder()
                .content(categories)
                .page(pageNumber)
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
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));
        categoryDTO.setId(id);
        Category category = modelMapper.map(categoryDTO,  Category.class);
        return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
    }
}
