package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.entity.Role;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();

        return userRepository.findByEmail(currentPrincipalEmail).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    public boolean isCurrentAuthenticatedUserAdmin() {
        User currentUser = getCurrentAuthenticatedUser();

        return currentUser.getRoles().stream().map(Role::getName).anyMatch(x -> x.equals("ROLE_ADMIN"));
    }

    public User getUserIfExists(UUID userId) {
        return (userId != null) ? getUserById(userId) : null;
    }
}
