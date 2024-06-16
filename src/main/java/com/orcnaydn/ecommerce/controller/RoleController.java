package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.role.CreateRoleDto;
import com.orcnaydn.ecommerce.entity.Role;
import com.orcnaydn.ecommerce.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("roles/{id}")
    public Role getRoleById(@PathVariable("id") Long roleId){
        return roleService.getRoleById(roleId);
    }

    @GetMapping("roles")
    public List<Role> getRoles(){
        return roleService.getAllRoles();
    }

    @PostMapping("roles")
    public Role createRole(@Valid @RequestBody CreateRoleDto dto){
        return roleService.createRole(dto);
    }

    @PutMapping("roles/{id}")
    public Role updateRole(@PathVariable("id") Long roleId, @Valid @RequestBody CreateRoleDto dto){
        return roleService.updateRole(roleId,dto);
    }

    @DeleteMapping("roles/{id}")
    public String deleteRole(@PathVariable("id") Long roleId){
        return roleService.deleteRole(roleId);
    }
}