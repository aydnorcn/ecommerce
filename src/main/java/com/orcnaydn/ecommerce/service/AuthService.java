package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.dto.auth.LoginRequest;
import com.orcnaydn.ecommerce.dto.auth.LoginResponse;
import com.orcnaydn.ecommerce.dto.auth.RegisterRequest;
import com.orcnaydn.ecommerce.dto.auth.RegisterResponse;
import com.orcnaydn.ecommerce.entity.Role;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.exception.AlreadyExistsException;
import com.orcnaydn.ecommerce.repository.UserRepository;
import com.orcnaydn.ecommerce.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return new LoginResponse(request.getEmail(), token);
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email already exists on database!");
        }

        Role role = roleService.getRoleByName("user");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoles(roles);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userRepository.save(user);
        return new RegisterResponse(request.getEmail(), request.getFirstName(), request.getLastName());
    }
}
