package com.heartbloom.be.app.api.auth;

import com.heartbloom.be.app.api.auth.response.GetLoginUrlResponse;
import com.heartbloom.be.app.api.auth.response.LoginResponse;
import com.heartbloom.be.app.service.auth.dto.TokenResult;
import com.heartbloom.be.app.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/oauth2/{provider}/login-url")
    public ResponseEntity<GetLoginUrlResponse> getLoginUrl(@PathVariable String provider,
                                                           @RequestParam String redirectUri,
                                                           @RequestParam String state) {
        String loginUrl = authService.getLoginUrl(redirectUri, state, provider);
        return ResponseEntity.ok(GetLoginUrlResponse.of(loginUrl, provider));
    }

    @PostMapping("/oauth2/{provider}/login")
    public ResponseEntity<LoginResponse> login(@PathVariable String provider,
                                               @RequestParam String code,
                                               @RequestParam String redirectUri) {
        TokenResult result = authService.login(code, redirectUri, provider);
        return ResponseEntity.ok(LoginResponse.of(result));
    }

}
