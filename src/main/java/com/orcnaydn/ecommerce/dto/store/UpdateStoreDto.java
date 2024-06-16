package com.orcnaydn.ecommerce.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateStoreDto {

    @NotBlank(message = "Store name cannot be empty or blank!")
    @Size(min = 3, max = 15, message = "Name length must be 3-15")
    private String name;
}