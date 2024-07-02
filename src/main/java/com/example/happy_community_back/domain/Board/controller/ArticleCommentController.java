package com.example.happy_community_back.domain.Board.controller;

import com.example.happy_community_back.domain.Board.dto.request.ArticleCommentReqDto;
import com.example.happy_community_back.domain.Board.dto.response.ArticleCommentResDto;
import com.example.happy_community_back.domain.Board.service.ArticleCommentService;
import com.example.happy_community_back.global.common.ApiResponseEntity;
import com.example.happy_community_back.global.common.ResponseText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles/{articleId}/comments")
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    /**
     * 댓글 목록 조회 API
     */
    @GetMapping()
    public ResponseEntity<List<ArticleCommentResDto>> getArticleComments(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleCommentService.getArticleComments(articleId));
    }

    /**
     * 댓글 단일 조회 API
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<ArticleCommentResDto> getArticleComment(@PathVariable Long commentId, @PathVariable String articleId) {
        return ResponseEntity.ok(articleCommentService.getArticleComment(commentId));
    }

    /**
     * 댓글 등록 API
     */
    @PostMapping("")
    public ResponseEntity<ApiResponseEntity<String>> addArticleComment(@PathVariable Long articleId, ArticleCommentReqDto.ArticleCommentAddReqDto articleCommentAddReqDto) {
        articleCommentService.addArticleComment(articleId, articleCommentAddReqDto);
        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_ADD_ARTICLE_COMMENT));
    }

    /**
     * 댓글 수정 API
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponseEntity<String>> modifyArticleComment(@PathVariable Long commentId, ArticleCommentReqDto.ArticleCommentModifyReqDto articleCommentModifyReqDto, @PathVariable String articleId) {
        articleCommentService.modifyArticleComment(commentId, articleCommentModifyReqDto);
        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_MODIFY_ARTICLE_COMMENT));
    }

    /**
     * 댓글 삭제 API
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponseEntity<String>> removeArticleComment(@PathVariable Long commentId, @PathVariable String articleId) {
        articleCommentService.removeArticleComment(commentId);
        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_REMOVE_ARTICLE_COMMENT));
    }

}
