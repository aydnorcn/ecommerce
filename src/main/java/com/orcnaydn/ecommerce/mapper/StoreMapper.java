package com.orcnaydn.ecommerce.mapper;

import com.orcnaydn.ecommerce.dto.response.StoreResponseDto;
import com.orcnaydn.ecommerce.entity.Store;
import com.orcnaydn.ecommerce.entity.User;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class StoreMapper {


    public static StoreResponseDto mapToDto(Store store){
        if(store == null) return null;
        return new StoreResponseDto(store.getId(),store.getName(),
                Optional.ofNullable(store.getOwners()).orElseGet(Collections::emptyList).stream().map(User::getId).collect(Collectors.toList()),
                Optional.ofNullable(store.getProducts()).orElseGet(Collections::emptyList).stream().map(ProductMapper::mapToDto).collect(Collectors.toList()),
                store.getTotalScore());
    }
}
