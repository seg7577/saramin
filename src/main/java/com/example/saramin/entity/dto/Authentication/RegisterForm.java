package com.example.saramin.entity.dto.Authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 등록을 위한 폼")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {
    @Schema(description = "사용자 로그인 이메일", example = "test@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "test1234")
    private String password;

    @Schema(description = "비밀번호 확인", example = "test1234")
    private String passwordCheck;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String username;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;
}
