package com.example.saramin.entity.dto.Authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원정보 수정을 위한 폼")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileForm {
    @Schema(description = "변경할 비밀번호", example = "test12345")
    private String newPassword;

    @Schema(description = "변경할 비밀번호 확인", example = "test12345")
    private String newPasswordCheck;

    @Schema(description = "변경할 전화번호", example = "010-8765-4321")
    private String newPhoneNumber;
}
