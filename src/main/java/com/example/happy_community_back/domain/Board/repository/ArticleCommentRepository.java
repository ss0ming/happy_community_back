package com.example.happy_community_back.domain.Board.repository;

import com.example.happy_community_back.domain.Board.entity.Article;
import com.example.happy_community_back.domain.Board.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    List<ArticleComment> findAllByArticleIdAndIsDeleted(Long articleID, Character isDeleted);

    int countByArticleIdAndIsDeleted(Long id, Character isDeleted);
}
