package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.exception.APIException;
import com.frosty.SpringBootECommerce.exception.ResourceNotFoundException;
import com.frosty.SpringBootECommerce.model.Category;
import com.frosty.SpringBootECommerce.payload.CategoryDTO;
import com.frosty.SpringBootECommerce.payload.ContentResponse;
import com.frosty.SpringBootECommerce.repository.CategoryRepository;
import com.frosty.SpringBootECommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceProvider implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ContentResponse<CategoryDTO> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Page<Category> categoryPage =  GlobalServiceHelper.getAllItems(pageNumber, pageSize, sortBy, sortOrder, categoryRepository);
        List<CategoryDTO> categories = GlobalServiceHelper.getDTOContent(categoryPage, CategoryDTO.class, modelMapper, "No Categories Found");
        return ContentResponse.<CategoryDTO>builder()
                .content(categories)
                .pageNumber(pageNumber)
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
        if(productRepository.existsByCategory_Id(categoryId)) {
            throw new APIException("Cannot delete category with id " + categoryId + " because it contains products");
        }
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
