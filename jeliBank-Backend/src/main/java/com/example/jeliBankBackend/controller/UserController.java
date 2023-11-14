package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.user.UserLoginDto;
import com.example.jeliBankBackend.dtos.requests.user.UserRegisterDto;
import com.example.jeliBankBackend.dtos.responses.user.UserAuthResponseDto;
import com.example.jeliBankBackend.model.Role;
import com.example.jeliBankBackend.model.Users;
import com.example.jeliBankBackend.repository.RolesRepository;
import com.example.jeliBankBackend.repository.UserRepository;
import com.example.jeliBankBackend.security.JwtGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class UserController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private RolesRepository rolesRepository;
    private UserRepository userRepository;
    private JwtGenerator jwtGenerator;

    public UserController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RolesRepository rolesRepository, UserRepository userRepository, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto){
        if (userRepository.existsByUsername(userRegisterDto.getUserName())){
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Users user = new Users();
        user.setUsername(userRegisterDto.getUserName());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        Role role = rolesRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return new ResponseEntity<>("Registro  de usuario tipo USER exitoso", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<UserAuthResponseDto> login(@RequestBody UserLoginDto dtoLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getUserName(), dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new UserAuthResponseDto(token), HttpStatus.OK);
    }

    // no necesito, en este caso el admin, pero queda la posibilidad de implementaciòn

//    @PostMapping("registerAdmin")
//    public ResponseEntity<String> registerAdmin(@RequestBody UserRegisterDto userRegisterDto) {
//        if (userRepository.existsByUsername(userRegisterDto.getUserName())){
//            return new ResponseEntity<>("el admin ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
//        }
//        Users user = new Users();
//        user.setUsername(userRegisterDto.getUserName());
//        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
//        Role role = rolesRepository.findByName("ADMIN").get();
//        user.setRoles(Collections.singletonList(role));
//        userRepository.save(user);
//        return new ResponseEntity<>("Registro  de usuario tipo ADMIN exitoso", HttpStatus.OK);
//    }



}
