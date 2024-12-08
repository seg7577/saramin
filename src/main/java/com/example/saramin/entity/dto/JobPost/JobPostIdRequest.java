package com.example.saramin.entity.dto.JobPost;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostIdRequest {
    @Schema(description = "북마크 추가/제거할 채용 공고의 Id", example = "1")
    private Long jobPostId;
}
