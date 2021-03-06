package com.briangroup.prontomenuadmin.controller;

import com.briangroup.prontomenuadmin.exception.ResourceNotFoundException;
import com.briangroup.prontomenuadmin.model.Category;
import com.briangroup.prontomenuadmin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/categories/{categoryId}")
    public Optional<Category> getCategoryById(@PathVariable(value = "categoryId") Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @PostMapping("/categories")
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/categories/{categoryId}")
    public  Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @Valid @RequestBody Category categoryRequest) {
        return categoryRepository.findById(categoryId).map(category -> {
            category.setName(categoryRequest.getName());
            category.setSubName(categoryRequest.getSubName());
            category.setDescription(categoryRequest.getDescription());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + " not found"));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
        return categoryRepository.findById(categoryId).map(category -> {
            categoryRepository.delete(category);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + " not found"));
    }
}
