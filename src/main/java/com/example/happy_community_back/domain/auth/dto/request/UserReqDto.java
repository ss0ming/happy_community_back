package com.example.happy_community_back.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserReqDto {

    @Builder
    public record UserModifyPasswordReqDto(
            @NotBlank(message = "비밀번호를 입력해주세요.")
            @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
            String password
    ) {

    }

    @Builder
    public record UserModifyNicknameReqDto(
            @NotBlank(message = "닉네임을 입력해주세요")
            @Size(max = 10, message = "닉네임은 10자 이하로 입력해주세요.")
            String nickname

    ) {

    }
}
