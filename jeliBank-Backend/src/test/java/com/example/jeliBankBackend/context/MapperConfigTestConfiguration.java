package com.example.jeliBankBackend.context;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import com.example.jeliBankBackend.config.MapperConfig;

@TestConfiguration
public class MapperConfigTestConfiguration {

    @Bean
    public MapperConfig mapperConfig() {
        return new MapperConfig();
    }
}