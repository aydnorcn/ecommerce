package com.orcnaydn.ecommerce.dto.response;

import com.orcnaydn.ecommerce.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductResponseDto {

    private UUID id;

    private String name;
    private String description;
    private Double price;

    private Category category;

    private Long store;
    private Float score;

//    private List<ReviewResponseDto> reviews;
//    private List<QuestionResponseDto> questions;
}