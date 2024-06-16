package com.orcnaydn.ecommerce.mapper;

import com.orcnaydn.ecommerce.dto.response.UserResponseDto;
import com.orcnaydn.ecommerce.entity.User;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponseDto mapToDto(User user){
        if(user == null) return null;
        return new UserResponseDto(user.getId(),user.getEmail(),user.getRoles(),user.getFirstName(),
                user.getLastName(),user.getPhoneNumber(),user.getAddress(),
                Optional.ofNullable(user.getOrders()).orElseGet(Collections::emptyList).stream().map(OrderMapper::mapToDto).collect(Collectors.toList()));
    }
}
