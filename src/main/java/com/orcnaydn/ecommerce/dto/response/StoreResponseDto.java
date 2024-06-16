package com.orcnaydn.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class StoreResponseDto {

    private Long id;
    private String name;

    private List<UUID> owners;

    private List<ProductResponseDto> products;
    private Float totalScore;
}