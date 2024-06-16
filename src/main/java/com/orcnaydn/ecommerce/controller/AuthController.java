package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.auth.LoginRequest;
import com.orcnaydn.ecommerce.dto.auth.LoginResponse;
import com.orcnaydn.ecommerce.dto.auth.RegisterRequest;
import com.orcnaydn.ecommerce.dto.auth.RegisterResponse;
import com.orcnaydn.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
}
