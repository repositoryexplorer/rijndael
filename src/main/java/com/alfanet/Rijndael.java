package com.alfanet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Rijndael {

    @Bean
    public CmdRunner cmdRunner() {
        return new CmdRunner();
    }

    public static void main(String[] args) {
        SpringApplication.run(Rijndael.class, args);
    }
}