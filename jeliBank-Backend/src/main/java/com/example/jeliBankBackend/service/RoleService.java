package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.dtos.requests.RoleDto;
import com.example.jeliBankBackend.dtos.requests.RoleRequestDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.mapper.RoleMapper;
import com.example.jeliBankBackend.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class RoleService {
    private final RolesRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(RolesRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleDto saveRole(RoleRequestDto roleRequestDTO) throws ResourseNotFoundException {
        try {
            return roleMapper.toDto(roleRepository.save(roleMapper.requestToRole(roleRequestDTO)));
        } catch (Exception e) {
            throw new ResourseNotFoundException("Internal Server Error occurred while saving role: " + e.getMessage());
        }
    }

    public List<RoleDto> getRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }
}
