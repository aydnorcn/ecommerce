package com.orcnaydn.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ReviewResponseDto {

    private Long id;

    private UUID user;
    private UUID product;

    private String review;
    private Integer score;
}