package com.heartbloom.be.app.api.contract;

import com.heartbloom.be.app.api.auth.response.GetLoginUrlResponse;
import com.heartbloom.be.app.api.auth.response.LoginResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "인증 API")
public interface AuthApi {

    @Operation(summary = "OAuth 로그인 URL 조회", description = "OAuth 제공자 로그인 페이지로 이동할 URL을 생성합니다.")
    ResponseEntity<ApiResponse<GetLoginUrlResponse>> getLoginUrl(
            @Parameter(description = "OAuth 제공자 타입", example = "kakao") String provider,
            @Parameter(description = "OAuth 인증 후 돌아올 redirect URI") String redirectUri,
            @Parameter(description = "OAuth state 값") String state
    );

    @Operation(summary = "OAuth 로그인", description = "OAuth 인가 코드로 서비스 access token을 발급합니다.")
    ResponseEntity<ApiResponse<LoginResponse>> login(
            @Parameter(description = "OAuth 제공자 타입", example = "kakao") String provider,
            @Parameter(description = "OAuth 인가 코드") String code,
            @Parameter(description = "OAuth 인증에 사용한 redirect URI") String redirectUri
    );

}
