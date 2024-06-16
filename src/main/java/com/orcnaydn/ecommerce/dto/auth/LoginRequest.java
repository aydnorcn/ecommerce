package com.orcnaydn.ecommerce.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "Email cannot be null or empty!")
    private String email;

    @NotNull(message = "Password cannot be null or empty!")
    private String password;
}
