package com.costa.luiz.zero2hero.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class BasicAuthenticationController {

    @GetMapping(path = "/basicauth")
    public AuthenticationBean basicAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        log.info("User {} has been authenticated", authentication.getPrincipal());
        log.info("This user has the following roles {}", roles);
        return new AuthenticationBean("You are authenticated", roles);
    }
}