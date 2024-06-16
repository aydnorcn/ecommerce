package com.orcnaydn.ecommerce.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUpdateCategoryDto {

    @NotNull(message = "Category name cannot be null or blank!")
    @Size(min = 5, max = 15, message = "Category name length must be between 5 and 15 characters")
    private String name;
}