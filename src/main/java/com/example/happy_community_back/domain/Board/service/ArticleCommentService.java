package com.example.happy_community_back.domain.Board.service;

import com.example.happy_community_back.domain.Board.dto.request.ArticleCommentReqDto.ArticleCommentAddReqDto;
import com.example.happy_community_back.domain.Board.dto.request.ArticleCommentReqDto.ArticleCommentModifyReqDto;
import com.example.happy_community_back.domain.Board.dto.response.ArticleCommentResDto;
import com.example.happy_community_back.domain.Board.entity.Article;
import com.example.happy_community_back.domain.Board.entity.ArticleComment;
import com.example.happy_community_back.domain.Board.repository.ArticleCommentRepository;
import com.example.happy_community_back.domain.Board.repository.ArticleRepository;
import com.example.happy_community_back.global.exception.CustomException;
import com.example.happy_community_back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentResDto> getArticleComments(final Long articleId) {
        List<ArticleComment> comments = articleCommentRepository.findAllByArticleId(articleId);
        List<ArticleCommentResDto> articleResDto = new ArrayList<>();

        for (ArticleComment comment : comments) {
            articleResDto.add(ArticleCommentResDto.of(comment));
        }

        return articleResDto;
    }

    @Transactional(readOnly = true)
    public ArticleCommentResDto getArticleComment(final Long commentId) {
        ArticleComment comment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_COMMENT_NOT_FOUND));

        return ArticleCommentResDto.of(comment);
    }

    @Transactional
    public void addArticleComment(final Long articleId, ArticleCommentAddReqDto articleCommentAddReqDto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

        ArticleComment articleComment = ArticleComment.addOf(articleCommentAddReqDto, article);
        articleCommentRepository.save(articleComment);
    }

    @Transactional
    public void modifyArticleComment(final Long commentId, ArticleCommentModifyReqDto articleCommentModifyReqDto) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_COMMENT_NOT_FOUND));

        articleComment.modify(articleCommentModifyReqDto);
        articleCommentRepository.save(articleComment);
    }

    @Transactional
    public void removeArticleComment(final Long commentId) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_COMMENT_NOT_FOUND));

        articleComment.remove();
        articleCommentRepository.save(articleComment);
    }
}
