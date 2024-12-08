package com.example.saramin.entity.dto.Authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 로그인을 위한 폼")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    @Schema(description = "사용자 로그인 이메일", example = "test@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "test1234")
    private String password;
}
