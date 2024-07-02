package com.example.happy_community_back.domain.Board.dto.response;

import com.example.happy_community_back.domain.Board.entity.ArticleComment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleCommentResDto(
        Long commentId,
        String content,
        LocalDateTime createdAt
) {
    public static ArticleCommentResDto of(ArticleComment articleComment) {
        return ArticleCommentResDto.builder()
                .commentId(articleComment.getId())
                .content(articleComment.getContent())
                .createdAt(articleComment.getCreatedAt())
                .build();
    }
}
