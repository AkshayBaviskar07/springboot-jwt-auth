package com.spring.security.Tutorial.dto;

import java.util.List;

public record UserResponseDto(
        Long id,
        String username,
        String email,
        String role
) {
}
