package com.example.happy_community_back.domain.Board.service;

import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleAddReqDto;
import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleModifyReqDto;
import com.example.happy_community_back.domain.Board.dto.response.ArticleCommentResDto;
import com.example.happy_community_back.domain.Board.dto.response.ArticleResDto;
import com.example.happy_community_back.domain.Board.entity.Article;
import com.example.happy_community_back.domain.Board.repository.ArticleCommentRepository;
import com.example.happy_community_back.domain.Board.repository.ArticleRepository;
import com.example.happy_community_back.domain.auth.entity.Member;
import com.example.happy_community_back.domain.auth.repository.MemberRepository;
import com.example.happy_community_back.global.exception.CustomException;
import com.example.happy_community_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    private final ArticleCommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<ArticleResDto> getArticles() {
        List<Article> articles = articleRepository.findAllByIsDeleted('n');
        List<ArticleResDto> articlesDto = new ArrayList<>();

        for (Article article : articles) {
            int commentCount = commentRepository.countByArticleIdAndIsDeleted(article.getId(), 'n');

            String imagePath = article.getMember().getProfileImage();

            String base64Image = null;
            File file = new File(imagePath);
            if (file.exists()) {
                try {
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    base64Image = Base64.getEncoder().encodeToString(fileContent);
                } catch (IOException e) {
                    throw new CustomException(ErrorCode.FILE_READ_ERROR);
                }
            }

            articlesDto.add(ArticleResDto.of(article, commentCount, base64Image));
        }

        return articlesDto;
    }

    @Transactional(readOnly = true)
    public ArticleResDto getArticle(final Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        if (article.getIsDeleted() != 'n') {
            throw new CustomException(ErrorCode.ARTICLE_COMMENT_NOT_FOUND);
        }

        int commentCount = commentRepository.countByArticleIdAndIsDeleted(article.getId(), 'n');

        String imagePath = article.getMember().getProfileImage();

        String base64Image = null;
        File file = new File(imagePath);
        if (file.exists()) {
            try {
                byte[] fileContent = Files.readAllBytes(file.toPath());
                base64Image = Base64.getEncoder().encodeToString(fileContent);
            } catch (IOException e) {
                throw new CustomException(ErrorCode.FILE_READ_ERROR);
            }
        }

        return ArticleResDto.of(article, commentCount, base64Image);
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
