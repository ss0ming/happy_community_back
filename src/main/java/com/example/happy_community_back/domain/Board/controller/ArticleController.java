package com.example.happy_community_back.domain.Board.controller;


import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleAddReqDto;
import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleModifyReqDto;
import com.example.happy_community_back.domain.Board.service.ArticleService;
import com.example.happy_community_back.domain.Board.dto.response.ArticleResDto;
import com.example.happy_community_back.global.common.ApiResponseEntity;
import com.example.happy_community_back.global.common.ResponseText;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 게시글 목록 조회 API
     */
    @GetMapping()
    public ResponseEntity<List<ArticleResDto>> getArticles() {
        return ResponseEntity.ok(articleService.getArticles());
    }

    /**
     * 게시글 상세 조회 API
     */
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResDto> getArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.getArticle(articleId));
    }

    /**
     * 게시글 등록 API
     */
    @PostMapping()
    public ResponseEntity<ApiResponseEntity<String>> addArticle(@Valid @RequestBody ArticleAddReqDto articleAddReqDto) {
        articleService.addArticle(articleAddReqDto);
        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_ADD_ARTICLE));
    }

    /**
     * 게시글 수정 API
     */
    @PutMapping("/{articleId}")
    public ResponseEntity<ApiResponseEntity<String>> modifyArticle(@PathVariable Long articleId, ArticleModifyReqDto articleModifyReqDto) {
        articleService.modifyArticle(articleModifyReqDto);
        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_MODIFY_ARTICLE));
    }

    /**
     * 게시글 삭제 API
     */
    @DeleteMapping("/{articleId}")
    public ResponseEntity<ApiResponseEntity<String>> removeArticle(@PathVariable Long articleId) {
        articleService.removeArticle(articleId);
        return ResponseEntity.ok(ApiResponseEntity.of(ResponseText.SUCCESS_REMOVE_ARTICLE));
    }
}
