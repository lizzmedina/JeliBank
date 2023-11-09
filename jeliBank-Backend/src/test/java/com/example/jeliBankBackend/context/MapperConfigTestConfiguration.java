package com.example.jeliBankBackend.context;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MapperConfigTestConfiguration {

    @Bean
    public MapperConfig mapperConfig() {
        return new MapperConfig();
    }
}