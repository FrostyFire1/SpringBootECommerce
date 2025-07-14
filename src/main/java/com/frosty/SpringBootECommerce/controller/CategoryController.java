package com.frosty.SpringBootECommerce.controller;

import com.frosty.SpringBootECommerce.model.Category;
import com.frosty.SpringBootECommerce.payload.CategoryDTO;
import com.frosty.SpringBootECommerce.payload.CategoryResponse;
import com.frosty.SpringBootECommerce.service.CategoryService;
import com.frosty.SpringBootECommerce.service.CategoryServiceProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(categoryService.getAllCategories(page, pageSize));
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createNewCategory(@Valid @RequestBody CategoryDTO category) {
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDTO, categoryId));
    }
}
