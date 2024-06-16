package com.orcnaydn.ecommerce.dto.response;

import com.orcnaydn.ecommerce.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private UUID id;

    private String email;
    private Set<Role> roles;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    private List<OrderResponseDto> orders;
}
