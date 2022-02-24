package com.costa.luiz.zero2hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Zero2heroApplication {

	public static void main(String[] args) {
		SpringApplication.run(Zero2heroApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {

	}

}
