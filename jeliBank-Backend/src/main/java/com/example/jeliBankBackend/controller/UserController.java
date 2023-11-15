package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.user.UserDto;
import com.example.jeliBankBackend.dtos.requests.user.UserRegisterDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.User;
import com.example.jeliBankBackend.repository.RolesRepository;
import com.example.jeliBankBackend.repository.UserRepository;
import com.example.jeliBankBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

   @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("{userId}/{roleId}")
    public ResponseEntity<UserDto> setRole(@PathVariable(name = "userId") Integer userId, @PathVariable(name = "roleId") Integer roleId) throws ResourseNotFoundException {
        return new ResponseEntity<>(userService.setRole(userId, roleId), HttpStatus.OK);
    }
}
