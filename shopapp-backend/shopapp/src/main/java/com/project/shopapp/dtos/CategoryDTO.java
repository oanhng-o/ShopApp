package com.project.shopapp.dtos;

import com.project.shopapp.models.Category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "Category name must not be empty")
    private String name;

    public static Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder().name(categoryDTO.getName()).build();
    }
}
