package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.RoleDto;
import com.example.jeliBankBackend.dtos.requests.RoleRequestDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles/")
public class RoleController {
    private final RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleRequestDto roleRequestDTO) throws ResourseNotFoundException {
        return new ResponseEntity<>(roleService.saveRole(roleRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles(){
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }
}
