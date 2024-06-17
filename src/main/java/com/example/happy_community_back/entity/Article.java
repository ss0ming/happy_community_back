package com.example.happy_community_back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

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
    @Column(nullable = false)
    @ColumnDefault("n")
    private Character isDeleted; // 삭제여부

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    protected Article () {}

    private Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Article of(String title, String content) {
        return new Article(title, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
