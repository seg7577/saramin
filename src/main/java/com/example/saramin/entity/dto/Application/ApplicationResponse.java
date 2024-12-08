package com.example.saramin.entity.dto.Application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {
    private Long id;
    private String jobTitle;
    private String userEmail;
    private String userName;
    private Long jobPostId;
    private LocalDateTime appliedAt;
}

