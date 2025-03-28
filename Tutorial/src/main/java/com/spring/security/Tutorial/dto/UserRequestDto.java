package com.spring.security.Tutorial.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserRequestDto(
        Long id,

        @NotNull(message = "Username is required")
        @Size(max = 20)
        String username,
        @NotNull(message = "Password is required")
        String password,
        @Email
        String email,
        Set<String> roles
) {
}
