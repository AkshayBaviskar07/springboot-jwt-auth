package com.spring.security.Tutorial.repo;

import com.spring.security.Tutorial.model.AppRole;
import com.spring.security.Tutorial.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(AppRole roleName);
}
