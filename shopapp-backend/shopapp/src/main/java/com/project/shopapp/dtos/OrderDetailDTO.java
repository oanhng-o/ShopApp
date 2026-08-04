package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @NotNull(message = "Order ID is required")
    @Min(value = 1, message = "Order ID must be greater than or equal to 1")
    private Integer orderId;

    @JsonProperty("product_id")
    @NotNull(message = "Product ID is required")
    @Min(value = 1, message = "Product ID must be greater than or equal to 1")
    private Integer productId;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @NotNull
    private Float price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "Number of products must be greater than or equal to 1")
    @NotNull
    private Integer numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    @NotNull
    private Float totalMoney;

    private String color;  
}
