package com.orcnaydn.ecommerce.dto.question;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUpdateQuestionDto {

    @NotNull(message = "Question cannot be null or blank!")
    @Size(min = 10, max = 100, message = "Question length must be between 10 and 100 characters")
    private String question;
}