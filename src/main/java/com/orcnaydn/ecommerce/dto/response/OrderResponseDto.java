package com.orcnaydn.ecommerce.dto.response;

import com.orcnaydn.ecommerce.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderResponseDto {

    private UUID id;

    private UUID user;

    private List<OrderItemResponseDto> items;
    private Double totalPrice;

    private OrderStatus status;
}