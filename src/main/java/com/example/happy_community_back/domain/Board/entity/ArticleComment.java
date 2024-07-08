package com.example.happy_community_back.domain.Board.entity;

import com.example.happy_community_back.domain.Board.dto.request.ArticleCommentReqDto.ArticleCommentAddReqDto;
import com.example.happy_community_back.domain.Board.dto.request.ArticleCommentReqDto.ArticleCommentModifyReqDto;
import com.example.happy_community_back.domain.auth.entity.Member;
import com.example.happy_community_back.global.config.db.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.Objects;

@Getter
@Entity
@Table(name = "comments")
public class ArticleComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("댓글 내용")
    private String content;

    @Column(name = "is_deleted",nullable = false)
    @ColumnDefault("'n'")
    @Comment("삭제여부")
    private Character isDeleted;

    @JoinColumn(name = "article_id")
    @ManyToOne(optional = false)
    @Comment("게시글")
    private Article article;

    @JoinColumn(name = "member_id")
    @ManyToOne(optional = false)
    @Comment("댓글 작성자")
    private Member member;

    protected ArticleComment () {}

    @Builder
    public ArticleComment(String content, Article article, Member member, Character isDeleted) {
        this.content = content;
        this.article = article;
        this.member = member;
        this.isDeleted = isDeleted != null ? isDeleted : 'n';
    }

    public static ArticleComment addOf(ArticleCommentAddReqDto dto, Article article, Member member) {
        return ArticleComment.builder()
                .content(dto.content())
                .article(article)
                .member(member)
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
