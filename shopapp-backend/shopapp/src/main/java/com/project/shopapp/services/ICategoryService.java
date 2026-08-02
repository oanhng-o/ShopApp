package com.project.shopapp.services;

import java.util.List;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(int id);
    List<Category> getAllCategories();
    Category updateCategory(int id, CategoryDTO category);
    void deleteCategory(int id);
}
