package com.example.happy_community_back.domain.Board.dto.response;

import com.example.happy_community_back.domain.Board.entity.ArticleComment;
import com.example.happy_community_back.domain.auth.entity.Member;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record ArticleCommentResDto(
        Long commentId,
        String content,
        String createdAt,
        String email,
        String nickname,
        String profileImage

) {
    public static ArticleCommentResDto of(ArticleComment articleComment, String base64Image) {
        Member member = articleComment.getMember();

        return ArticleCommentResDto.builder()
                .commentId(articleComment.getId())
                .content(articleComment.getContent())
                .createdAt(articleComment.getCreatedAt().format(DateTimeFormatter.ofPattern("yy-MM-dd hh:mm:ss")))
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(base64Image)
                .build();
    }
}
