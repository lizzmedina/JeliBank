package com.example.jeliBankBackend.utils;

import com.example.jeliBankBackend.model.Role;
import com.example.jeliBankBackend.model.User;
import com.example.jeliBankBackend.repository.RolesRepository;
import com.example.jeliBankBackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    public Runner(UserRepository userRepository, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (this.rolesRepository.count() == 0) {
            this.rolesRepository.saveAll(List.of(
                    new Role(RolesName.ADMIN),
                    new Role(RolesName.READ),
                    new Role(RolesName.WRITE)
            ));
        }

        if (this.userRepository.count() == 0) {
            var encoders = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            this.userRepository.saveAll(List.of(
                            new User("liza09", encoders.encode("liza123"), List.of(this.rolesRepository.findByRole(RolesName.ADMIN).get())),
                            new User("user01", "user01123", List.of(this.rolesRepository.findByRole(RolesName.READ).get())),
                            new User("user02", "user02123", List.of(this.rolesRepository.findByRole(RolesName.WRITE).get()))
                    )
            );
        }
    }
}
