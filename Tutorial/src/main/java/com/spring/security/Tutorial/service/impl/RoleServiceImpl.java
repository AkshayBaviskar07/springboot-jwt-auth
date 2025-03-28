package com.spring.security.Tutorial.service.impl;

import com.spring.security.Tutorial.exception.RoleNotFoundException;
import com.spring.security.Tutorial.model.AppRole;
import com.spring.security.Tutorial.model.Role;
import com.spring.security.Tutorial.repo.RoleRepo;
import com.spring.security.Tutorial.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;


    @Override
    public Role findByRoleName(AppRole roleName) {
        return roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName));
    }
}
