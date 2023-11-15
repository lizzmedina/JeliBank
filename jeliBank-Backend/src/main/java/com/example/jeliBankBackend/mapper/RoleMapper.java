package com.example.jeliBankBackend.mapper;


import com.example.jeliBankBackend.dtos.requests.RoleDto;
import com.example.jeliBankBackend.dtos.requests.RoleRequestDto;
import com.example.jeliBankBackend.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toRole(RoleDto roleDTO);
    @Mapping(target = "role", expression = "java(roleRequestDTO.getRole().toUpperCase())")
    @Mapping(target = "idRole", ignore = true)
    Role requestToRole(RoleRequestDto roleRequestDTO);
}
