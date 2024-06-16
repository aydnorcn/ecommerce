package com.orcnaydn.ecommerce.mapper;

import com.orcnaydn.ecommerce.dto.response.OrderItemResponseDto;
import com.orcnaydn.ecommerce.dto.response.OrderResponseDto;
import com.orcnaydn.ecommerce.entity.Order;
import com.orcnaydn.ecommerce.entity.OrderItem;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDto mapToDto(Order order){
        if(order == null) return null;
        return new OrderResponseDto(order.getId(),order.getUser().getId(),
                Optional.ofNullable(order.getItems()).orElseGet(Collections::emptyList).stream().map(OrderMapper::mapToDtoItem).collect(Collectors.toList()),
                order.getTotalPrice(),order.getStatus());
    }

    public static OrderItemResponseDto mapToDtoItem(OrderItem item){
        return new OrderItemResponseDto(item.getId(),item.getOrder().getId(),item.getStore().getId(),
                item.getName(),item.getDescription(),item.getQuantity(),item.getEachPrice(),item.getTotalPrice(),item.getCategory());
    }
}
