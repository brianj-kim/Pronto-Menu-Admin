package com.briangroup.prontomenuadmin.controller;

import com.briangroup.prontomenuadmin.exception.ResourceNotFoundException;
import com.briangroup.prontomenuadmin.model.Category;
import com.briangroup.prontomenuadmin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public Page<Category> getAllCategories(@PageableDefault(size = 999) Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @PostMapping("/categories")
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/categories/{categoryId}")
    public  Category updateCategory(@PathVariable Long categoryId, @Valid @RequestBody Category categoryRequest) {
        return categoryRepository.findById(categoryId).map(category -> {
            category.setName(categoryRequest.getName());
            category.setSubName(categoryRequest.getSubName());
            category.setDescription(categoryRequest.getDescription());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + " not found"));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        return categoryRepository.findById(categoryId).map(category -> {
            categoryRepository.delete(category);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + " not found"));
    }
}
