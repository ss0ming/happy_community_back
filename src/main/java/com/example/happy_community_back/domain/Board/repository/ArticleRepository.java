package com.example.happy_community_back.domain.Board.repository;

import com.example.happy_community_back.domain.Board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
