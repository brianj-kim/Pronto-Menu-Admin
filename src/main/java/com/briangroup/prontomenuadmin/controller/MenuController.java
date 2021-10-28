package com.briangroup.prontomenuadmin.controller;

import com.briangroup.prontomenuadmin.exception.ResourceNotFoundException;
import com.briangroup.prontomenuadmin.model.Menu;
import com.briangroup.prontomenuadmin.repository.CategoryRepository;
import com.briangroup.prontomenuadmin.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MenuController {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories/{categoryId}/menus")
    public Page<Menu> getAllMenuByCategoryId(@PathVariable (value = "categoryId") Long categoryId,
                                             Pageable pageable) {
        return menuRepository.findByCategoryId(categoryId, pageable);
    }

    @PostMapping("/categories/{categoryId}/menus")
    public Menu creeateMenu(@PathVariable (value = "categoryId") Long categoryId,
                            @Valid @RequestBody Menu menu) {
        return categoryRepository.findById(categoryId).map(category -> {
            menu.setCategory(category);
            return menuRepository.save(menu);
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + " not found"));
    }

    @PutMapping("/categories/{categoryId}/menus/{menuId}")
    public Menu updateMenu(@PathVariable (value = "categoryId") Long categoryId,
                           @PathVariable (value = "menuId") Long menuId,
                           @Valid @RequestBody Menu menuRequest) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("CategoryId " + categoryId + " not found");
        }

        return menuRepository.findById(menuId).map(menu -> {
            menu.setName(menuRequest.getName());
            menu.setDescription(menuRequest.getDescription());
            menu.setPrice(menuRequest.getPrice());
            menu.setVegetarian(menuRequest.isVegetarian());
            menu.setDelivered(menuRequest.isDelivered());
            menu.setAvailable(menuRequest.isAvailable());
            menu.setSpicyLevel(menuRequest.getSpicyLevel());
            return menuRepository.save(menu);
        }).orElseThrow(() -> new ResourceNotFoundException("MenuId " + menuId + " not found"));
    }

    @DeleteMapping("/categories/{categoryId}/menus/{menuId}")
    public ResponseEntity<?> deleteMenu(@PathVariable (value = "categoryId") Long categoryId,
                                        @PathVariable (value = "menuId") Long menuId) {
        return menuRepository.findByIdAndCategoryId(menuId, categoryId).map(menu -> {
            menuRepository.delete(menu);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Menu not found with id " + menuId + " and categoryId " + categoryId));
    }
}
