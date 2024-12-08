package com.example.saramin.service;

import com.example.saramin.auth.JwtToken;
import com.example.saramin.auth.JwtTokenProvider;
import com.example.saramin.customException.CustomExceptions;
import com.example.saramin.entity.dto.Authentication.RefreshForm;
import com.example.saramin.entity.model.RefreshToken;
import com.example.saramin.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Map<String, Object> refresh(RefreshForm refreshForm) throws CustomExceptions.BadRequestException, CustomExceptions.UnauthorizedException {
        Map<String, Object> response = new LinkedHashMap<>();

        RefreshToken refreshToken = refreshTokenRepository.findAllByValue(refreshForm.getRefreshToken());

        if (refreshToken == null || !refreshForm.getEmail().equals(refreshToken.getEmail())) {
            throw new CustomExceptions.BadRequestException("유효하지 않은 토큰입니다");
        }

        try {
            JwtToken jwtToken = jwtTokenProvider.refreshAccessToken(refreshForm.getAccessToken(), refreshForm.getRefreshToken());

            refreshToken.setValue(jwtToken.getRefreshToken());
            refreshToken.setExpireDate(jwtToken.getExpireDate());

            refreshTokenRepository.save(refreshToken);

            response.put("status", "success");
            response.put("jwtToken", jwtToken);

            return response;
        } catch (RuntimeException e) {
            throw new CustomExceptions.UnauthorizedException(e.getMessage());
        }
    }
}