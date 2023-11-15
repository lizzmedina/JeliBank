package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.dtos.requests.user.UserLoginDto;
import com.example.jeliBankBackend.dtos.requests.user.UserRegisterDto;
import com.example.jeliBankBackend.dtos.responses.user.UserAuthResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.User;
import com.example.jeliBankBackend.repository.RolesRepository;
import com.example.jeliBankBackend.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RolesRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, RolesRepository roleRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    public UserAuthResponseDto login(UserLoginDto loginDTO) throws ResourseNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
        UserDetails user = userRepository.findByUsername(loginDTO.getUserName()).orElseThrow(() -> new ResourseNotFoundException("User not found"));
        String token = jwtService.getToken(user);
        return new UserAuthResponseDto(token);
    }
    public UserAuthResponseDto register(UserRegisterDto registerDTO) throws ResourseNotFoundException {
        User user;
        try {
            user = new User(
                    0,
                    registerDTO.getFirstName(),
                    registerDTO.getLastName(),
                    registerDTO.getUsername(),
                    passwordEncoder.encode(registerDTO.getPassword()),
                    roleRepository.findByRole("USER").getIdRole()
                    );
            userRepository.save(user);
        } catch (Exception e) {
            throw new ResourseNotFoundException("Internal Server Error occurred while saving user: " + e.getMessage());
        }
        return new UserAuthResponseDto(jwtService.getToken(user));
    }
}