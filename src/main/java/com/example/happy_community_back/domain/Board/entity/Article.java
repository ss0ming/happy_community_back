package com.example.happy_community_back.domain.Board.entity;

import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleAddReqDto;
import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleModifyReqDto;
import com.example.happy_community_back.domain.auth.entity.Member;
import com.example.happy_community_back.global.config.db.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Getter
@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 ID

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String content; // 본문

    private String image; // 이미지

    @Column(nullable = false)
    @ColumnDefault("0")
    private int likes; // 좋아요수

    @Column(nullable = false)
    @ColumnDefault("0")
    private int viewCount; // 조회수

    @JoinColumn(name = "member_id")
    @ManyToOne(optional = false)
    private Member member;

    @Column(nullable = false)
    @ColumnDefault("'n'")
    private Character isDeleted; // 삭제여부

    protected Article () {}

    @Builder
    public Article(String title, String content, String image, Member member, Character isDeleted) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.likes = 0;
        this.viewCount = 0;
        this.member = member;
        this.isDeleted = isDeleted != null ? isDeleted : 'n';
    }

    public static Article addOf(ArticleAddReqDto dto, Member member) {
        return Article.builder()
                .title(dto.title())
                .content(dto.content())
                .member(member)
                .build();
    }

    public void modify(ArticleModifyReqDto dto) {
        this.title = dto.title();
        this.content = dto.content();
    }

    public void remove() {
        this.isDeleted = 'y';
    }
}
