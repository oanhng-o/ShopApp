package com.project.shopapp.dtos;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 200, message = "Product name must be between 3 and 200 characters")
    private String name;
    
    @Min(value = 0, message = "Price must be a positive value")
    @Max(value = 100000000, message = "Price must not exceed 100,000,000")
    private float price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id")
    private String categoryId;

    private MultipartFile file;
}
