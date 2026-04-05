package com.santiwrld.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SantiWrldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SantiWrldApplication.class, args);
    }

}
