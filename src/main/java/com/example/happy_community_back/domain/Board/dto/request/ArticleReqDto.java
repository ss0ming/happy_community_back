package com.example.happy_community_back.domain.Board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleReqDto {

    @Builder
    public record ArticleAddReqDto(
            @NotBlank(message = "제목을 입력해주세요.")
            @Size(max = 26, message = "제목은 26자 이하로 입력해주세요.")
            String title,

            @NotBlank(message = "내용을 작성해주세요.")
            String content,

            String image
    ) {

    }

    @Builder
    public record ArticleModifyReqDto(
            @NotBlank(message = "제목을 입력해주세요.")
            @Size(max = 26, message = "제목은 26자 이하로 입력해주세요.")
            String title,

            @NotBlank(message = "내용을 작성해주세요.")
            String content,
            String image
    ) {

    }
}
