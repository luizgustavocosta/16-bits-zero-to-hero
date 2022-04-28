package com.costa.luiz.zero2hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Zero2heroApplication {

    public static void main(String[] args) {
        SpringApplication.run(Zero2heroApplication.class, args);
    }

}
