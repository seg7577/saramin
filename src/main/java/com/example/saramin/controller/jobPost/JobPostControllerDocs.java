package com.example.saramin.controller.jobPost;

import com.example.saramin.entity.dto.JobPost.JobPostUpdateForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface JobPostControllerDocs {

    @Operation(summary = "채용 공고 목록 조회", description = "채용 공고 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채용 공고 조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"200\", "
                                    + "\"message\": \"채용 공고 조회 성공\", "
                                    + "\"currentPage\": 1, "
                                    + "\"totalPages\": 1, "
                                    + "\"totalElements\": 1, "
                                    + "\"jobPosts\": ["
                                    + "{"
                                    + "\"id\": 1, "
                                    + "\"title\": \"임베디드(HW) 개발자\", "
                                    + "\"deadline\": \"2024-12-20T15:00:00.000+00:00\", "
                                    + "\"postDate\": \"2024-11-21T19:36:46.631+00:00\""
                                    + "}"
                                    + "]"
                                    + "}"))),
            @ApiResponse(responseCode = "400", description = "채용 공고 조회 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"페이지 번호가 잘못되었습니다\"}")))
    })
    ResponseEntity<Map<String, Object>> getJobPosts(
            @Parameter(description = "현재 페이지 번호", example = "1") Integer currentPage,
            @Parameter(description = "정렬 기준: newest(최신순) 혹은 alphabetical(가나다순)", example = "newest", required = false)
            @RequestParam(required = false) String sortBy,
            @Parameter(description = "지역 필터(null일 경우 필터링하지 않음)", example = "서울전체", required = false)
            @RequestParam(required = false) String workplace,
            @Parameter(description = "경력 필터(null일 경우 필터링하지 않음) 예시의 경우 경력 5년 이하를 의미", example = "5", required = false)
            @RequestParam(required = false) Integer career,
            @Parameter(description = "기술 스택 필터(null일 경우 필터링하지 않음) 그누보드, 라즈베리파이 등", example = "그누보드", required = false)
            @RequestParam(required = false) String skillStack,
            @Parameter(description = "검색 방식(null일 경우 검색하지 않음) title(제목) 혹은 companyName(회사명)", example = "title", required = false)
            @RequestParam(required = false) String searchBy,
            @Parameter(description = "검색에 사용할 키워드", example = "개발", required = false)
            @RequestParam(required = false) String keyword);

    @Operation(summary = "채용 공고 상세 조회", description = "특정 ID의 채용 공고를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채용 공고 조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"200\", "
                                    + "\"message\": \"채용 공고 조회 성공\", "
                                    + "\"jobPost\": {"
                                    + "\"id\": 1, "
                                    + "\"title\": \"다우기술 경력사원 대규모 인재영입\", "
                                    + "\"skillStack\": \"그누보드\", "
                                    + "\"workPlace\": \"경기 성남시 분당구 외\", "
                                    + "\"career\": \"경력 2년↑ · 정규직\", "
                                    + "\"careerMin\": 2, "
                                    + "\"careerMax\": 2, "
                                    + "\"education\": \"대학(2,3년)↑\", "
                                    + "\"postDate\": \"2024-11-28T08:35:12.189+00:00\", "
                                    + "\"deadline\": \"2024-12-11T08:35:12.188+00:00\", "
                                    + "\"viewCount\": 1"
                                    + "}"
                                    + "}"))),
            @ApiResponse(responseCode = "404", description = "채용 공고를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"404\", \"message\": \"존재하지 않는 채용 공고입니다.\"}")))
    })
    ResponseEntity<Map<String, Object>> getJobPostDetail(Long id);

    @Operation(summary = "채용 공고 수정", description = "특정 ID의 채용 공고를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채용 공고 수정 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"200\", "
                                    + "\"message\": \"채용 공고 수정 성공\", "
                                    + "\"jobPost\": {"
                                    + "\"id\": 1, "
                                    + "\"title\": \"Java 개발자 채용\", "
                                    + "\"skillStack\": \"아두이노\", "
                                    + "\"workPlace\": \"서울전체\", "
                                    + "\"career\": \"신입\", "
                                    + "\"careerMin\": 0, "
                                    + "\"careerMax\": 999, "
                                    + "\"education\": \"대졸 이상\", "
                                    + "\"postDate\": \"2024-11-28T08:35:12.189+00:00\", "
                                    + "\"deadline\": \"2024-12-07T08:37:28.619+00:00\", "
                                    + "\"viewCount\": 1"
                                    + "}"
                                    + "}"))),
            @ApiResponse(responseCode = "400", description = "채용 공고 수정 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"채용 공고 수정 중 오류가 발생했습니다.\"}")))
    })
    ResponseEntity<Map<String, Object>> updateJobPost(Long id, JobPostUpdateForm updateForm);

    @Operation(summary = "채용 공고 삭제", description = "특정 ID의 채용 공고를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채용 공고 삭제 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"200\", \"message\": \"채용 공고 삭제 성공\"}"))),
            @ApiResponse(responseCode = "404", description = "채용 공고를 찾을 수 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"404\", \"message\": \"존재하지 않는 채용 공고입니다.\"}")))
    })
    ResponseEntity<Map<String, Object>> deleteJobPost(Long id);
}