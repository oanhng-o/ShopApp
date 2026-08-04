package com.project.shopapp.dtos.products;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 200, message = "Product name must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be a positive value")
    @Max(value = 100000000, message = "Price must not exceed 100,000,000")
    private Float price;

    @NotBlank
    private String categoryName;
}
