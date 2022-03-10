package com.costa.luiz.zero2hero.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class AuthenticationBean {

    private final String message;
    private final Set<String> roles;

}