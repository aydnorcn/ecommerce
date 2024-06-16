package com.orcnaydn.ecommerce.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateReviewDto {

    @NotNull(message = "Review cannot be null or blank!")
    @Size(min = 10, max = 100, message = "Review length must be between 10 and 100 characters")
    private String review;

    @Min(value = 1, message = "Score must be 1 minimum")
    @Max(value = 5, message = "Score must be 5 maximum")
    @NotNull(message = "Score cannot be null!")
    private Integer score;
}
