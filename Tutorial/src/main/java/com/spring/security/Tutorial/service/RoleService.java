package com.spring.security.Tutorial.service;

import com.spring.security.Tutorial.model.AppRole;
import com.spring.security.Tutorial.model.Role;


public interface RoleService {

    Role findByRoleName(AppRole roleName);
}
