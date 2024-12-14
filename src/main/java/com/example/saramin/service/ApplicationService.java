package com.example.saramin.service;

import com.example.saramin.customException.CustomExceptions;
import com.example.saramin.entity.dto.Application.ApplicationForm;
import com.example.saramin.entity.dto.Application.ApplicationResponse;
import com.example.saramin.entity.model.Application;
import com.example.saramin.entity.model.JobPost;
import com.example.saramin.entity.model.User;
import com.example.saramin.repository.ApplicationRepository;
import com.example.saramin.repository.JobPostRepository;
import com.example.saramin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;

    public Map<String, Object> application(ApplicationForm applicationRequest, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomExceptions.BadRequestException("사용자를 찾을 수 없습니다."));

        JobPost jobPost = jobPostRepository.findById(applicationRequest.getJobPostId())
                .orElseThrow(() -> new CustomExceptions.BadRequestException("존재하지 않는 채용 공고입니다."));

        if (applicationRepository.existsByUserAndJobPost(user, jobPost)) {
            throw new CustomExceptions.BadRequestException("이미 지원한 공고입니다.");
        }

        Application application = Application.builder()
                .user(user)
                .jobPost(jobPost)
                .appliedAt(LocalDateTime.now())
                .build();

        applicationRepository.save(application);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "200");
        response.put("message", "지원 성공");
        return response;
    }

    public Map<String, Object>  getApplications(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomExceptions.BadRequestException("사용자를 찾을 수 없습니다."));

        List<ApplicationResponse> applications = applicationRepository.findByUser(user).stream()
                .map(app -> new ApplicationResponse(
                        app.getId(),
                        app.getJobPost().getTitle(),
                        user.getEmail(),
                        user.getProfile().getUsername(),
                        app.getJobPost().getId(),
                        app.getAppliedAt()))
                .sorted((a1, a2) -> a2.getAppliedAt().compareTo(a1.getAppliedAt()))
                .toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "200");
        response.put("message", "지원 내역 조회 성공");
        response.put("applications", applications);
        return response;
    }

    public Map<String, Object>  cancelApplication(Long id, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomExceptions.BadRequestException("사용자를 찾을 수 없습니다."));

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.BadRequestException("존재하지 않는 지원 내역입니다."));

        if (!application.getUser().equals(user)) {
            throw new CustomExceptions.UnauthorizedException("해당 지원을 취소할 권한이 없습니다.");
        }

        applicationRepository.delete(application);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "200");
        response.put("message", "지원 취소 성공");
        return response;
    }
}
