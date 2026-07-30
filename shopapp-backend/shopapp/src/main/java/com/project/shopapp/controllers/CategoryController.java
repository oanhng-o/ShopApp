package com.project.shopapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import com.project.shopapp.dtos.CategoryDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
public class CategoryController {

    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("List of categories (Page: %d, Limit: %d)", page, limit));
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok("Category created: " + categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Category updated with ID: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Category deleted with ID: " + id);
    }
}
