package com.costa.luiz.zero2hero.business.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class Authentication {

    private final String message;
    private final Set<String> roles;
    private final boolean authenticated;
    private final String user;

}