package com.spring.security.Tutorial.controller;

import com.spring.security.Tutorial.dto.LoginRequestDto;
import com.spring.security.Tutorial.dto.UserRequestDto;
import com.spring.security.Tutorial.dto.UserResponseDto;
import com.spring.security.Tutorial.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserServiceImpl userService;

    /*@Value("${spring.security.user.name}")
    private String userName;

    @GetMapping("/home")
    public String home() {
        return "Hello " + userName;
    }*/

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUserSignIn(@RequestBody @Valid LoginRequestDto loginRequest) {
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto userRequest) {
        return ResponseEntity.ok(userService.registerUser(userRequest));
    }

}
