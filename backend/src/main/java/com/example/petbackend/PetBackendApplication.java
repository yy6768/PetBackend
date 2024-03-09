package com.example.petbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class PetBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetBackendApplication.class, args);
    }

}
