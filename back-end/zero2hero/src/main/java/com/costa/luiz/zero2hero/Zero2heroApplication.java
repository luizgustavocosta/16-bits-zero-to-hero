package com.costa.luiz.zero2hero;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class Zero2heroApplication {

    public static void main(String[] args) {
        SpringApplication.run(Zero2heroApplication.class, args);
        log.debug("Application started");
    }

}
