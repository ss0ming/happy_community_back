package com.example.happy_community_back.domain.auth.service;

import com.example.happy_community_back.domain.auth.enums.Role;
import com.example.happy_community_back.domain.auth.enums.TokenName;
import com.example.happy_community_back.global.auth.jwt.component.JwtTokenProvider;
import com.example.happy_community_back.global.auth.jwt.dto.AccessTokenDto;
import com.example.happy_community_back.global.exception.CustomException;
import com.example.happy_community_back.global.exception.ErrorCode;
import com.example.happy_community_back.global.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenRefreshService {

    private final JwtTokenProvider jwtTokenProvider;

    public AccessTokenDto userRefresh(HttpServletRequest request) {
        final String refreshToken = CookieUtils.getCookieValue(request.getCookies(), TokenName.USER_REFRESH_TOKEN.name());
        return refresh(refreshToken, Role.ROLE_USER);
    }

    /**
     * Refresh Token으로 Access Token 갱신
     */
    private AccessTokenDto refresh(final String refreshToken, Role role) {
        if (StringUtils.isBlank(refreshToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        // JWT 토큰에서 UserName 추출
        String userName = jwtTokenProvider.getUserName(refreshToken);
        if (StringUtils.isBlank(userName)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        // Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(userName, "", List.of(role::name));

        return jwtTokenProvider.generateAccessToken(authentication);
    }
}
