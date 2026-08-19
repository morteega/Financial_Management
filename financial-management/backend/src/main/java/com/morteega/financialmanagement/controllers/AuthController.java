package com.morteega.financialmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morteega.financialmanagement.dtos.UserResponseDTO;
import com.morteega.financialmanagement.services.AuthService;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(String email, String password){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authService.registerUser(email, password));
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> login(String email, String password){
        return ResponseEntity.ok(this.authService.login(email, password));
    }

}
