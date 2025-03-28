package com.spring.security.Tutorial.dto;

import lombok.Data;

public record LoginRequestDto(
    String username,
    String password
) {}
