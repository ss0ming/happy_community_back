package com.example.happy_community_back.domain.Board.dto.response;

import com.example.happy_community_back.domain.Board.entity.Article;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleResDto(
        Long articleId,
        String title,
        String content,
        String image,
        int likes,
        int viewCount,
        LocalDateTime createdAt
) {
    public static ArticleResDto of(Article article) {
        return ArticleResDto.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .image(article.getImage())
//                .likes(article.getLikes())
//                .viewCount(article.getViewCount())
                .createdAt(article.getCreatedAt())
                .build();
    }
}
