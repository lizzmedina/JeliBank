package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.dtos.requests.user.UserDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.mapper.UserMapper;
import com.example.jeliBankBackend.model.User;
import com.example.jeliBankBackend.repository.UserRepository;
import org.hibernate.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto setRole(Integer userId, Integer roleId) throws ResourseNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PropertyNotFoundException("Airport not found with ID: " + userId));
        user.setRoleId(roleId);
        try {
            return userMapper.toDto(userRepository.save(user));
        } catch (Exception e) {
            throw new ResourseNotFoundException("Internal Server Error occurred while updating user: " + e.getMessage());
        }
    }
}
