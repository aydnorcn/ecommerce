package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.dto.role.CreateRoleDto;
import com.orcnaydn.ecommerce.entity.Role;
import com.orcnaydn.ecommerce.exception.AlreadyExistsException;
import com.orcnaydn.ecommerce.exception.NoAuthorityException;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserService userService;

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(CreateRoleDto dto) {
        if (roleRepository.existsByName("ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH))) {
            throw new AlreadyExistsException("Role already exists!");
        }

        if (!userService.isCurrentAuthenticatedUserAdmin())
            throw new NoAuthorityException("You don't have permission!");

        Role role = new Role();
        String roleName = "ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH);
        role.setName(roleName);
        return roleRepository.save(role);
    }

    public Role getRoleByName(String name) {
        String roleName = "ROLE_" + name.toUpperCase(Locale.ENGLISH);
        return roleRepository.findByName(roleName).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }

    public Role updateRole(Long id, CreateRoleDto dto) {
        if (!userService.isCurrentAuthenticatedUserAdmin())
            throw new NoAuthorityException("You don't have permission!");

        Role updateRole = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        String roleName = "ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH);
        updateRole.setName(roleName);
        return roleRepository.save(updateRole);
    }

    public String deleteRole(Long id) {
        if (!userService.isCurrentAuthenticatedUserAdmin())
            throw new NoAuthorityException("You don't have permission!");
        roleRepository.deleteById(id);
        return "Role removed!";
    }
}
