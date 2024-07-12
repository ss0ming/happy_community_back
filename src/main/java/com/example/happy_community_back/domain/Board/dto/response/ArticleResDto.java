package com.example.happy_community_back.domain.Board.dto.response;

import com.example.happy_community_back.domain.Board.entity.Article;
import com.example.happy_community_back.domain.auth.entity.Member;
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
        LocalDateTime createdAt,
        String email,
        String nickname,
        String profileImage,
        int commentCount
) {
    public static ArticleResDto of(Article article, int commentCount) {
        Member member = article.getMember();

        return ArticleResDto.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .image(article.getImage())
                .likes(article.getLikes())
                .viewCount(article.getViewCount())
                .createdAt(article.getCreatedAt())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .commentCount(commentCount)
                .build();
    }
}
