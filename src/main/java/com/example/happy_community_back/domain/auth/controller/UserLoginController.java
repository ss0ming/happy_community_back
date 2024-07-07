package com.example.happy_community_back.domain.auth.controller;

import com.example.happy_community_back.domain.auth.dto.request.LoginReqDto;
import com.example.happy_community_back.domain.auth.dto.request.UserSignUpReqDto;
import com.example.happy_community_back.domain.auth.service.TokenRefreshService;
import com.example.happy_community_back.domain.auth.service.UserLoginService;
import com.example.happy_community_back.global.auth.jwt.dto.AccessTokenDto;
import com.example.happy_community_back.global.common.ApiResponseEntity;
import com.example.happy_community_back.global.common.ResponseText;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;

    private final TokenRefreshService tokenRefreshService;

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity<AccessTokenDto>> login(@RequestBody LoginReqDto loginReqDto, HttpServletResponse response) {
        AccessTokenDto accessTokenDto = userLoginService.login(loginReqDto, response);

        return ResponseEntity.ok(ApiResponseEntity.of(accessTokenDto));
    }

    /**
     * 회원가입 API
     */
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponseEntity<String>> signUp(@RequestBody UserSignUpReqDto dto) {
        userLoginService.signUp(dto);

        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_SIGN_UP));
    }

    /**
     * 이메일 중복 체크 API
     */
    @GetMapping("/check-duplicate-email")
    public ResponseEntity<ApiResponseEntity<String>> checkDuplicateEmail(@RequestParam String email) {
        boolean isDuplicate = userLoginService.checkDuplicateEmail(email);

        return ResponseEntity.ok(ApiResponseEntity.of(isDuplicate ? ResponseText.DUPLICATE : ResponseText.OK));
    }

    /**
     * 닉네임 중복 체크 API
     */
    @GetMapping("/check-duplicate-nickname")
    public ResponseEntity<ApiResponseEntity<String>> checkDuplicateNickname(@RequestBody String nickname) {
        boolean isDuplicate = userLoginService.checkDuplicateNickname(nickname);

        return ResponseEntity.ok(ApiResponseEntity.of(isDuplicate ? ResponseText.DUPLICATE : ResponseText.OK));
    }

    /**
     * 토큰 갱신 API
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseEntity<AccessTokenDto>> refresh(HttpServletRequest request) {
        AccessTokenDto accessTokenDto = tokenRefreshService.userRefresh(request);

        return ResponseEntity.ok(ApiResponseEntity.of(accessTokenDto));
    }

    /**
     * 로그아웃 API
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseEntity<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        userLoginService.logout(request, response);

        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_LOGOUT));
    }
}
