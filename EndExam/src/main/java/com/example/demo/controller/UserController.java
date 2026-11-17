package com.example.demo.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public Map<String, Object> profile(Authentication authentication) {

        return Map.of(
                "message", "JWT Authentication Successful",
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }
}