package com.example.saramin.entity.dto.Application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationForm {

    @Schema(description = "지원할 채용 공고의 ID", example = "1")
    private Long jobPostId;

    @Schema(description = "지원서 파일의 경로", example = "/uploads/resumes/user_resume.pdf")
    private String resume;
}


