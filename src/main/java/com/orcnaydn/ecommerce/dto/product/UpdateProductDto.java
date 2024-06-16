package com.orcnaydn.ecommerce.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProductDto {

    @NotNull(message = "Product name cannot be null or blank!")
    @Size(min = 5, max = 30, message = "Product name length must be between 5 and 30 characters")
    private String name;

    @NotNull(message = "Description cannot be null or blank!")
    @Size(min = 20, max = 150, message = "Description length must be between 20 and 150 characters")
    private String description;

    @Min(0)
    @NotNull(message = "Price cannot be null!")
    private Double price;

    @Min(1)
    @NotNull(message = "Store ID cannot be null!")
    private Long storeId;

    @Min(1)
    @NotNull(message = "Category ID cannot be null!")
    private Long categoryId;
}