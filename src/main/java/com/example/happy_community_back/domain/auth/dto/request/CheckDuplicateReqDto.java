package com.example.happy_community_back.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckDuplicateReqDto {

    public record CheckDuplicateEmailReqDto(
            @NotBlank(message = "이메일을 입력해주세요.")
            String email
    ) {

    }

    public record CheckDuplicateNicknameReqDto(
            @NotBlank(message = "닉네임을 입력해주세요.")
            String nickname
    ) {

    }
}
