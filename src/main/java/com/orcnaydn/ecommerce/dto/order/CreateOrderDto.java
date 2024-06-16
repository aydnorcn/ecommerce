package com.orcnaydn.ecommerce.dto.order;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class CreateOrderDto {

    @Size(min=1, message = "You need to add at least 1 product for apply order")
    private List<ProductQuantity> products;

    @Getter
    public static class ProductQuantity{
        private UUID productId;
        private Integer quantity;
    }
}