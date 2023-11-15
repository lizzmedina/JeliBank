package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.user.UserLoginDto;
import com.example.jeliBankBackend.dtos.requests.user.UserRegisterDto;
import com.example.jeliBankBackend.dtos.responses.user.UserAuthResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<UserAuthResponseDto> login(@RequestBody UserLoginDto loginDTO) throws ResourseNotFoundException {
        return new ResponseEntity<>(authService.login(loginDTO), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<UserAuthResponseDto> register(@RequestBody UserRegisterDto registerDTO) throws ResourseNotFoundException {
        return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.OK);
    }
}
