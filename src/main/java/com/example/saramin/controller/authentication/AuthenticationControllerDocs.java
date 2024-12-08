package com.example.saramin.controller.authentication;

import com.example.saramin.customException.CustomExceptions;
import com.example.saramin.entity.dto.Authentication.LoginForm;
import com.example.saramin.entity.dto.Authentication.ProfileForm;
import com.example.saramin.entity.dto.Authentication.RefreshForm;
import com.example.saramin.entity.dto.Authentication.RegisterForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface AuthenticationControllerDocs {

    @Operation(summary = "회원가입", description = "신규 사용자의 회원가입을 위한 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"200\", \"message\": \"회원가입 성공\"}"))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"입력하지 않은 값이 존재합니다\"}")))
    })
    ResponseEntity<Map<String, Object>> register(@RequestBody RegisterForm registerForm) throws CustomExceptions.BadRequestException;

    @Operation(summary = "로그인", description = "사용자의 인증을 위한 로그인 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"200\", "
                                    + "\"message\": \"로그인 성공\", "
                                    + "\"user\": {"
                                    + "\"id\": 1, "
                                    + "\"email\": \"test@gmail.com\", "
                                    + "\"username\": \"홍길동\", "
                                    + "\"phoneNumber\": \"010-1234-5678\""
                                    + "}, "
                                    + "\"jwtToken\": {"
                                    + "\"grantType\": \"Bearer\", "
                                    + "\"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNzMzMzg4MjcxfQ.vUUuDEJdPXprfeyriunwDoBQh8555iXB4mRMafpmAvs\", "
                                    + "\"refreshToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MzM1NTkyNzF9.DX0BxeRtF6ztaCwlmcitzXN_SWB2JLUc9xA85mBP2bc\", "
                                    + "\"expireDate\": \"2024-12-07T08:14:31.557+00:00\""
                                    + "}"
                                    + "}"))),
            @ApiResponse(responseCode = "400", description = "로그인 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"잘못된 이메일 또는 비밀번호입니다. 다시 시도해주세요.\"}")))
    })
    ResponseEntity<Map<String, Object>> login(@RequestBody LoginForm loginForm) throws CustomExceptions.BadRequestException;

    @Operation(summary = "Jwt 재발급", description = "Jwt 재발급을 위한 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"200\", "
                                    + "\"message\": \"토큰 재발급 성공\", "
                                    + "\"jwtToken\": {"
                                    + "\"grantType\": \"Bearer\", "
                                    + "\"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNzMzMzY3NzQwfQ.HhNAo5NvGRV3Z65DmjONQwNk8p00NnllYJVsxHvO53c\", "
                                    + "\"refreshToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MzM1Mzg3NDB9.8rcldpvomm2J-sON9sHMThU93dwxZqdW4Z0xT_ObPTI\", "
                                    + "\"expireDate\": \"2024-12-07T02:32:20.239+00:00\""
                                    + "}"
                                    + "}"))),
            @ApiResponse(responseCode = "400", description = "토큰 재발급 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"유효하지 않은 토큰입니다\"}")))
    })
    ResponseEntity<Map<String, Object>> refresh(@RequestBody RefreshForm refreshForm) throws CustomExceptions.BadRequestException;

    @Operation(summary = "회원정보 수정", description = "비밀번호 또는 사용자 전화번호를 수정하기 위한 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원정보 수정 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"200\", "
                                    + "\"message\": \"회원정보 수정 성공\", "
                                    + "\"user\": {"
                                    + "\"id\": 1, "
                                    + "\"email\": \"test@gmail.com\", "
                                    + "\"username\": \"홍길동\", "
                                    + "\"phoneNumber\": \"010-8765-4321\""
                                    + "}"
                                    + "}"))),
            @ApiResponse(responseCode = "400", description = "회원정보 수정 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"400\", \"message\": \"비밀번호와 비밀번호 확인이 일치하지 않습니다\"}"))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"401\", \"message\": \"사용자가 인증되지 않았습니다\"}")))
    })
    ResponseEntity<Map<String, Object>> profile(Authentication auth, @RequestBody ProfileForm profileForm) throws CustomExceptions.UnauthorizedException, CustomExceptions.BadRequestException;
}