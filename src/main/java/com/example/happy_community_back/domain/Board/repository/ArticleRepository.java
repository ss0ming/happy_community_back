package com.example.happy_community_back.domain.Board.repository;

import com.example.happy_community_back.domain.Board.dto.response.ArticleResDto;
import com.example.happy_community_back.domain.Board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByIsDeleted(Character isDeleted);
}
