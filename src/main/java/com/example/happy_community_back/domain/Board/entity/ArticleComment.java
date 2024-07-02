package com.example.happy_community_back.domain.Board.entity;

import com.example.happy_community_back.domain.Board.dto.request.ArticleCommentReqDto.ArticleCommentAddReqDto;
import com.example.happy_community_back.domain.Board.dto.request.ArticleCommentReqDto.ArticleCommentModifyReqDto;
import com.example.happy_community_back.global.config.db.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Getter
@Entity
public class ArticleComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 ID

    @Column(nullable = false)
    private String content; // 본문
    @Column(nullable = false)
    @ColumnDefault("n")
    private Character isDeleted; // 삭제여부

    @JoinColumn(name = "article_id")
    @ManyToOne(optional = false)
    private Article article; // 게시글 (ID)

    protected ArticleComment () {}

    @Builder
    public ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment addOf(ArticleCommentAddReqDto dto, Article article) {
        return ArticleComment.builder()
                .content(dto.content())
                .article(article)
                .build();
    }

    public void modify(ArticleCommentModifyReqDto dto) {
        this.content = dto.content();
    }

    public void remove() {
        this.isDeleted = 'y';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
