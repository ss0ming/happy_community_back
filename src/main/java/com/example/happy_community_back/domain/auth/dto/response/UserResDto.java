package com.example.happy_community_back.domain.auth.dto.response;

import com.example.happy_community_back.domain.auth.entity.Member;
import lombok.Builder;

@Builder
public record UserResDto(
        String email,
        String nickname,
        String profileImage
) {
    public static UserResDto of(Member member, String base64Image){
        return UserResDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(base64Image)
                .build();
    }
}
