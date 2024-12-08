package com.example.saramin.controller.bookmark;

import com.example.saramin.entity.dto.JobPost.JobPostIdRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface BookmarkControllerDocs {

    @Operation(summary = "북마크 추가/제거", description = "사용자가 채용 공고를 북마크 추가하거나 제거합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 처리 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"200\", \"message\": \"북마크 추가 성공\", \"title\": \"채용 공고 제목\"}"))),
            @ApiResponse(responseCode = "400", description = "채용 공고를 찾을 수 없습니다",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"채용 공고를 찾을 수 없습니다\"}"))),
            @ApiResponse(responseCode = "401", description = "사용자를 찾을 수 없습니다",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"401\", \"message\": \"사용자가 인증되지 않았습니다\"}")))
    })
    ResponseEntity<Map<String, Object>> toggleBookmark(
            Authentication authentication,
            @RequestBody JobPostIdRequest jobPostIdRequest
    );

    @Operation(summary = "사용자의 북마크 목록 조회", description = "사용자가 북마크한 채용 공고 목록을 페이지네이션하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"200\", \"message\": \"사용자의 북마크된 채용 공고 목록입니다.\", \"totalPages\": 3, \"currentPage\": 1, \"bookmarkedJobPosts\": [{\"title\": \"채용 공고 제목\", \"id\": \"1\", \"bookmarkedAt\": \"2024-12-07T10:15:30\"}]}"))),
            @ApiResponse(responseCode = "401", description = "사용자를 찾을 수 없습니다",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"401\", \"message\": \"사용자가 인증되지 않았습니다\"}")))
    })
    ResponseEntity<Map<String, Object>> getUserBookmarks(
            Authentication authentication,
            @Parameter(description = "현재 페이지 번호", example = "1") int currentPage
    );
}
