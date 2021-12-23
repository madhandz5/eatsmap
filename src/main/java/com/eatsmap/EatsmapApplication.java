package com.eatsmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableJpaAuditing
@EnableJpaRepositories
@EnableAspectJAutoProxy
@SpringBootApplication
public class EatsmapApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatsmapApplication.class, args);
    }

}
