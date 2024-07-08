package com.example.happy_community_back.domain.Board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleCommentReqDto {

    @Builder
    public record ArticleCommentAddReqDto(
            @NotBlank(message = "댓글을 작성해주세요.")
            String content
    ) {

    }

    @Builder
    public record ArticleCommentModifyReqDto(
            @NotBlank(message = "댓글을 작성해주세요.")
            String content
    ) {

    }
}
