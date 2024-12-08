package com.example.saramin.controller.jobPost;

import com.example.saramin.entity.dto.JobPost.JobPostUpdateForm;
import com.example.saramin.entity.dto.JobPost.JobsRequestForm;
import com.example.saramin.service.JobPostService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Tag(name = "Jobs", description = "채용 공고 관련 API")
public class JobPostController implements JobPostControllerDocs {
    private final JobPostService jobPostService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getJobPosts(
            Integer currentPage,
            String sortBy,
            String workplace,
            Integer career,
            String skillStack,
            String searchBy,
            String keyword) {

        JobsRequestForm requestForm = new JobsRequestForm(currentPage, sortBy, workplace, career, skillStack, searchBy, keyword);

        Map<String, Object> response = jobPostService.getJobPosts(requestForm);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getJobPostDetail(
            @Parameter(description = "채용 공고 ID", example = "1") @PathVariable Long id) {
        Map<String, Object> response = jobPostService.getJobPostDetail(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateJobPost(
            @Parameter(description = "채용 공고 ID", example = "1") @PathVariable Long id,
            @RequestBody JobPostUpdateForm updateForm) {
        Map<String, Object> response = jobPostService.updateJobPost(id, updateForm);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteJobPost(
            @Parameter(description = "채용 공고 ID", example = "1") @PathVariable Long id) {

        Map<String, Object> response = jobPostService.deleteJobPost(id);
        return ResponseEntity.ok(response);
    }
}