package com.example.saramin.controller.application;

import com.example.saramin.entity.dto.Application.ApplicationForm;
import com.example.saramin.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController implements ApplicationControllerDocs {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> application(@RequestBody ApplicationForm applicationForm, Authentication authentication) {
        Map<String, Object> response = applicationService.application(applicationForm, authentication);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getApplications(Authentication authentication) {
        Map<String, Object> response = applicationService.getApplications(authentication);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{jobPostId}")
    public ResponseEntity<Map<String, Object>> cancelApplication(Authentication authentication,
                                                                 @PathVariable Long jobPostId) {
        Map<String, Object> response = applicationService.cancelApplication(jobPostId, authentication);
        return ResponseEntity.ok(response);
    }
}