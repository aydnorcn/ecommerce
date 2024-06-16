package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.response.UserResponseDto;
import com.orcnaydn.ecommerce.mapper.UserMapper;
import com.orcnaydn.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users/{id}")
    public UserResponseDto getUserById(@PathVariable("id") UUID userId){
        return UserMapper.mapToDto(userService.getUserById(userId));
    }

    @GetMapping("users")
    public List<UserResponseDto> getUsers(){
        return userService.getAllUsers().stream().map(UserMapper::mapToDto).collect(Collectors.toList());
    }
}