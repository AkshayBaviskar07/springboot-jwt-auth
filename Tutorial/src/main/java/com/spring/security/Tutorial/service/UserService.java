package com.spring.security.Tutorial.service;

import com.spring.security.Tutorial.dto.LoginRequestDto;
import com.spring.security.Tutorial.dto.UserRequestDto;
import com.spring.security.Tutorial.dto.UserResponseDto;
import com.spring.security.Tutorial.jwt.JwtAuthenticationResponse;

import java.util.List;

public interface UserService {

    JwtAuthenticationResponse authenticateUser(LoginRequestDto loginRequest);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Long id, UserRequestDto userRequest);

    UserResponseDto getUserById(Long id);

    String deleteUserById(Long id);

    UserResponseDto registerUser(UserRequestDto userRequest);
}
