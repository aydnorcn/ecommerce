package com.orcnaydn.ecommerce.dto.answer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUpdateAnswerDto {

    @NotNull(message = "Answer cannot be null or blank!")
    @Size(min = 10, max = 100, message = "Answer length must be between 10 and 100 characters")
    private String message;
}