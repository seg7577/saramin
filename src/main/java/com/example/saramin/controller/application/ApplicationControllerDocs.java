package com.example.saramin.controller.application;

import com.example.saramin.entity.dto.Application.ApplicationForm;
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

public interface ApplicationControllerDocs {

    @Operation(summary = "지원하기", description = "사용자가 채용 공고에 지원합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지원 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"200\", \"message\": \"지원 성공\"}"))),
            @ApiResponse(responseCode = "400", description = "지원 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"이미 지원한 공고입니다.\"}")))
    })
    ResponseEntity<Map<String, Object>> application(@RequestBody ApplicationForm applicationRequest, Authentication authentication);

    @Operation(summary = "지원 내역 조회", description = "사용자의 지원 내역을 최신순으로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지원 내역 조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"200\", "
                                    + "\"message\": \"지원 내역 조회 성공\", "
                                    + "\"applications\": [{"
                                    + "\"id\": 1, "
                                    + "\"jobTitle\": \"임베디드(HW) 개발자\", "
                                    + "\"userEmail\": \"test@gmail.com\", "
                                    + "\"userName\": \"홍길동\", "
                                    + "\"jobPostId\": 1, "
                                    + "\"appliedAt\": \"2024-12-07T08:14:31\""
                                    + "}]"
                                    + "}"))),
            @ApiResponse(responseCode = "404", description = "지원 내역을 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"지원 내역이 없습니다.\"}")))
    })
    ResponseEntity<Map<String, Object>> getApplications(Authentication authentication);

    @Operation(summary = "지원 취소", description = "사용자가 지원을 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지원 취소 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"200\", \"message\": \"지원 취소 성공\"}"))),
            @ApiResponse(responseCode = "400", description = "지원 취소 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"존재하지 않는 지원 내역입니다.\"}"))),
            @ApiResponse(responseCode = "401", description = "권한 없음",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"401\", \"message\": \"해당 지원을 취소할 권한이 없습니다.\"}")))
    })
    ResponseEntity<Map<String, Object>> cancelApplication(Authentication authentication,
                                                          @Parameter(description = "취소할 지원의 id", example = "1")
                                                          Long jobPostId);
}