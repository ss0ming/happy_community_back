package com.example.happy_community_back.domain.Board.service;

import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleAddReqDto;
import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleModifyReqDto;
import com.example.happy_community_back.domain.Board.dto.response.ArticleResDto;
import com.example.happy_community_back.domain.Board.entity.Article;
import com.example.happy_community_back.domain.Board.repository.ArticleRepository;
import com.example.happy_community_back.domain.auth.entity.Member;
import com.example.happy_community_back.domain.auth.repository.MemberRepository;
import com.example.happy_community_back.global.exception.CustomException;
import com.example.happy_community_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<ArticleResDto> getArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleResDto> articlesDto = new ArrayList<>();

        for (Article article : articles) {
            articlesDto.add(ArticleResDto.of(article));
        }

        return articlesDto;
    }

    @Transactional(readOnly = true)
    public ArticleResDto getArticle(final Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        return ArticleResDto.of(article);
    }

    @Transactional
    public void addArticle(ArticleAddReqDto articleAddReqDto, String email) {
        // 현재 로그인한 사용자의 이메일로 Member 객체를 조회
        Member member = memberRepository.findById(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER_ID));

        Article article = Article.addOf(articleAddReqDto, member);
        articleRepository.save(article);
    }

    @Transactional
    public void modifyArticle(final Long articleId, ArticleModifyReqDto articleModifyReqDto) {
        Article article = articleRepository.findById(articleId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        article.modify(articleModifyReqDto);
        articleRepository.save(article);
    }

    @Transactional
    public void removeArticle(final long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        article.remove();
        articleRepository.save(article);
    }

}
