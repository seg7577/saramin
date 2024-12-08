package com.example.saramin.entity.dto.Authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Access Token 재발급 요청을 위한 폼")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshForm {
    @Schema(description = "사용자 이메일", example = "test@gmail.com")
    String email;

    @Schema(description = "로그인을 통해 발급된 jwt의 access token", example = "access token value")
    String accessToken;

    @Schema(description = "로그인을 통해 발급된 jwt의 refresh token", example = "refresh token value")
    String refreshToken;
}
