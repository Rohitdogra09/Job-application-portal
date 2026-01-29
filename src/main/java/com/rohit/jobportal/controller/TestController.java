package com.rohit.jobportal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/me")
    public Object me(Authentication auth) {
        Jwt jwt = (Jwt) auth.getPrincipal();
        return Map.of(
                "username", auth.getName(),
                "authorities", auth.getAuthorities(),
                "claims", jwt.getClaims()
        );
    }

}
