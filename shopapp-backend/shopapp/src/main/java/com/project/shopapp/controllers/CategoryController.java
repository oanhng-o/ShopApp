package com.project.shopapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dtos.ApiResponse;
import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.services.impl.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.success(
                String.format("Retrieved categories successfully, page: &d, limit: %d.", page, limit), categories));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(ApiResponse.success("Create category successfully.", category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable int id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity
                .ok(ApiResponse.success(String.format("Category updated successfully with ID: %d.", id), category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ResponseEntity
                .ok(ApiResponse.success(String.format("Delete category successfully with ID: %d.", id), null));
    }
}
