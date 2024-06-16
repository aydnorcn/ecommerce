package com.orcnaydn.ecommerce.dto.response;

import com.orcnaydn.ecommerce.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderItemResponseDto {

    private Long id;

    private UUID order;
    private Long store;

    private String name;
    private String description;

    private Integer quantity;
    private Double eachPrice;
    private Double totalPrice;

    private Category category;
}