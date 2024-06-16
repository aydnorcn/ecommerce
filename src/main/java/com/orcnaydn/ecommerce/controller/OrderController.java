package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.order.CreateOrderDto;
import com.orcnaydn.ecommerce.dto.response.OrderResponseDto;
import com.orcnaydn.ecommerce.entity.Order;
import com.orcnaydn.ecommerce.mapper.OrderMapper;
import com.orcnaydn.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("orders/{id}")
    public OrderResponseDto getOrderById(@PathVariable("id") UUID orderId){
        return OrderMapper.mapToDto(orderService.getOrderById(orderId));
    }

    @GetMapping("orders")
    public PageResponseDto<OrderResponseDto> getOrders(@RequestParam(name = "user",required = false) UUID userId, @RequestParam(name = "minPrice", required = false) Double minPrice, @RequestParam(name = "maxPrice", required = false) Double maxPrice,
                                            @RequestParam(name = "status", required = false) Integer status,
                                            @RequestParam(name = "pageNo", defaultValue = "0",required = false) int pageNo,
                                            @RequestParam(name = "pageSize", defaultValue = "10",required = false) int pageSize){
        PageResponseDto<Order> orders = orderService.getOrders(userId,minPrice,maxPrice,status,pageNo,pageSize);

        return new PageResponseDto<>(orders.getContent().stream().map(OrderMapper::mapToDto).collect(Collectors.toList()),orders.getPageNo(),orders.getPageSize(),orders.getTotalElements(),orders.getTotalPages());
    }

    @PostMapping("orders")
    public OrderResponseDto createOrder(@Valid @RequestBody CreateOrderDto dto){
        return OrderMapper.mapToDto(orderService.createOrder(dto));
    }

    @DeleteMapping("orders/{id}")
    public String deleteOrder(@PathVariable("id") UUID orderId){
        return orderService.deleteOrder(orderId);
    }
}