package com.costa.luiz.zero2hero.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicAuthenticationController {

    @GetMapping(path = "/basicauth")
    public AuthenticationBean basicAuth() {
        // Get the user data here
        return new AuthenticationBean("You are authenticated");
    }
}