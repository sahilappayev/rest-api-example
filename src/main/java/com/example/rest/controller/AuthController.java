package com.example.rest.controller;

import com.example.rest.dto.AuthTokenResponseDto;
import com.example.rest.dto.UserRequestDto;
import com.example.rest.dto.UserResponseDto;
import com.example.rest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponseDto> loginUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.loginUser(userRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokenResponseDto> refreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(refreshToken));
    }

}
