package com.orcnaydn.ecommerce.utils;

import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;

public enum OrderStatus {
    PREPARING(0),
    CANCELLED(1),
    ON_DELIVERY(2),
    DELIVERED(3);

    final Integer id;

    OrderStatus(int id){
        this.id = id;
    }

    public static OrderStatus getById(int id){
        for(OrderStatus status : OrderStatus.values()){
            if(status.id.equals(id)) return status;
        }
        throw new ResourceNotFoundException("Status not found!");
    }
}
