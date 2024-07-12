package com.example.happy_community_back.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpReqDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordConfirm;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max = 10, message = "닉네임은 10자 이하로 입력해주세요.")
    private String nickname;

    private String image;

    public void modifyPassword(String password) {
        this.password = password;
    }

    public void modifyImage(String path) {
        this.image = path;
    }
}
