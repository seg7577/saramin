package com.example.saramin.controller.authentication;

import com.example.saramin.entity.dto.Authentication.LoginForm;
import com.example.saramin.entity.dto.Authentication.ProfileForm;
import com.example.saramin.entity.dto.Authentication.RefreshForm;
import com.example.saramin.entity.dto.Authentication.RegisterForm;
import com.example.saramin.service.RefreshTokenService;
import com.example.saramin.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "사용자 및 인증 관련 API")
public class AuthenticationController implements AuthenticationControllerDocs {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterForm registerForm) {
        Map<String, Object> response = userService.register(registerForm);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginForm loginForm) {
        Map<String, Object> response = userService.login(loginForm);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refresh(@RequestBody RefreshForm refreshForm) {
        Map<String, Object> response = refreshTokenService.refresh(refreshForm);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<Map<String, Object>> profile(Authentication auth, @RequestBody ProfileForm profileForm) {
        Map<String, Object> response = userService.profile(auth, profileForm);
        return ResponseEntity.ok(response);
    }
}