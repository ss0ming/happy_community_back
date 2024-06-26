package com.example.happy_community_back.domain.Board.repository;

import com.example.happy_community_back.domain.Board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
