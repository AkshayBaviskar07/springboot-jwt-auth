package com.spring.security.Tutorial.service.impl;

import com.spring.security.Tutorial.dto.LoginRequestDto;
import com.spring.security.Tutorial.dto.UserRequestDto;
import com.spring.security.Tutorial.dto.UserResponseDto;
import com.spring.security.Tutorial.exception.UserNotFoundException;
import com.spring.security.Tutorial.jwt.JwtAuthenticationResponse;
import com.spring.security.Tutorial.jwt.JwtUtils;
import com.spring.security.Tutorial.model.AppRole;
import com.spring.security.Tutorial.model.User;
import com.spring.security.Tutorial.model.Role;
import com.spring.security.Tutorial.repo.UserRepo;
import com.spring.security.Tutorial.service.RoleService;
import com.spring.security.Tutorial.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    public JwtAuthenticationResponse authenticateUser(LoginRequestDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        return new JwtAuthenticationResponse(jwtToken);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole().getRoleName().toString()
                ))
                .collect(Collectors.toList());
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequest) {
        User user = userRepo.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user.setUsername(userRequest.username());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setEmail(userRequest.email());

        if (userRequest.roles() != null && !userRequest.roles().isEmpty()) {
            String roleName = userRequest.roles().iterator().next();
            Role role = roleService.findByRoleName(AppRole.valueOf("ROLE_" + roleName));
            user.setRole(role);
        } else {
            // if the role is null, set the default role
            Role defaultRole = roleService.findByRoleName(AppRole.ROLE_USER);
            user.setRole(defaultRole);
        }

        userRepo.save(user);

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getRoleName().toString()
        );
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getRoleName().toString()
        );
    }

    public String deleteUserById(Long id) {
        if(!userRepo.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
        return "User deleted successfully";
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequest) {

        if (userRepo.existsByUsername(userRequest.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepo.existsByEmail(userRequest.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));

        // if the role is not null, set the role
        if (userRequest.roles() != null && !userRequest.roles().isEmpty()) {
            String roleName = userRequest.roles().iterator().next();
            Role role = roleService.findByRoleName(AppRole.valueOf("ROLE_" + roleName));
            user.setRole(role);
        } else {
            // if the role is null, set the default role
            Role defaultRole = roleService.findByRoleName(AppRole.ROLE_USER);
            user.setRole(defaultRole);
        }
        User savedUser = userRepo.save(user);
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().getRoleName().toString()
        );
    }
}
