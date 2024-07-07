package com.example.happy_community_back.global.auth.jwt.dto;

import lombok.Builder;

@Builder
public record TokenDto(
        AccessTokenDto accessTokenDto,
        RefreshTokenDto refreshTokenDto
) {

}
