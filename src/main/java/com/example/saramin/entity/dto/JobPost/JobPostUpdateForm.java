package com.example.saramin.entity.dto.JobPost;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class JobPostUpdateForm {
    @Schema(description = "채용 공고의 제목", example = "Java 개발자 채용")
    private String title;

    @Schema(description = "요구되는 기술 스택", example = "아두이노")
    private String skillStack;

    @Schema(description = "근무지 정보", example = "서울전체")
    private String workPlace;

    @Schema(description = "경력 요구 사항", example = "신입")
    private String career;

    @Schema(description = "학력 요구 사항", example = "대졸 이상")
    private String education;

    @Schema(description = "지원 마감일", example = "2024-12-31")
    private Date deadline;
}

