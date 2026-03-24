package com.esieeit.projetsi.api.controller;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security-test")
public class SecurityTestController {

    @GetMapping("/public/ping")
    public Map<String, String> publicPing() {
        return Map.of("message", "Route publique OK");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/ping")
    public Map<String, String> userPing() {
        return Map.of("message", "Route USER OK");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/ping")
    public Map<String, String> adminPing() {
        return Map.of("message", "Route ADMIN OK");
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/common/ping")
    public Map<String, String> commonPing() {
        return Map.of("message", "Route USER/ADMIN OK");
    }
}
