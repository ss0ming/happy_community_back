package com.example.happy_community_back.domain.Board.controller;

import com.example.happy_community_back.domain.Board.dto.request.ArticleCommentReqDto.ArticleCommentAddReqDto;
import com.example.happy_community_back.domain.Board.dto.request.ArticleCommentReqDto.ArticleCommentModifyReqDto;
import com.example.happy_community_back.domain.Board.dto.response.ArticleCommentResDto;
import com.example.happy_community_back.domain.Board.service.ArticleCommentService;
import com.example.happy_community_back.global.common.ResponseText;
import com.example.happy_community_back.global.exception.CustomException;
import com.example.happy_community_back.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ArticleCommentController.class)
@ExtendWith(MockitoExtension.class)
class ArticleCommentControllerTest {

    @MockBean
    private ArticleCommentService articleCommentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private final long articleId = 1L;

    private final long commentId = 1L;

    @Nested
    @DisplayName("댓글 목록 조회")
    class 댓글_목록_조회 {

        @Test
        @DisplayName("성공")
        void 성공() throws Exception {
            // given
            List<ArticleCommentResDto> comments = Collections.singletonList(getCommentResDto());
            given(articleCommentService.getArticleComments(articleId)).willReturn(comments);

            // when & then
            ResultActions actions = mockMvc.perform(get("/api/articles/{articleId}/comments", articleId)
                    .contentType("application/json"));

            actions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$[0].commentId").value(commentId))
                    .andExpect(jsonPath("$[0].content").value("댓글입니다."));

            then(articleCommentService).should().getArticleComments(articleId);
        }

        private ArticleCommentResDto getCommentResDto() {
            return ArticleCommentResDto.builder()
                    .commentId(commentId)
                    .content("댓글입니다.")
                    .build();
        }
    }

    @Nested
    @DisplayName("댓글 단일 조회")
    class 댓글_단일_조회 {

        @Test
        @DisplayName("성공")
        void 성공() throws Exception {
            // given
            ArticleCommentResDto commentResDto = getCommentResDto();
            given(articleCommentService.getArticleComment(commentId)).willReturn(commentResDto);

            // when & then
            ResultActions actions = mockMvc.perform(get("/api/articles/{articleId}/comments/{commentId}", articleId, commentId)
                    .contentType("application/json"));

            actions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.commentId").value(commentId))
                    .andExpect(jsonPath("$.content").value("댓글입니다."));

            then(articleCommentService).should().getArticleComment(commentId);
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 댓글 ID")
        void 실패_존재하지_않는_댓글_ID() throws Exception {
            // given
            given(articleCommentService.getArticleComment(commentId)).willThrow(new CustomException(ErrorCode.ARTICLE_COMMENT_NOT_FOUND));

            // when & then
            ResultActions actions = mockMvc.perform(get("/api/articles/{articleId}/comments/{commentId}", articleId, commentId)
                    .contentType("application/json"));

            actions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(ErrorCode.ARTICLE_COMMENT_NOT_FOUND.getMessage()));

            then(articleCommentService).should().getArticleComment(commentId);
        }

        private ArticleCommentResDto getCommentResDto() {
            return ArticleCommentResDto.builder()
                    .commentId(commentId)
                    .content("댓글입니다.")
                    .build();
        }
    }

    @Nested
    @DisplayName("댓글 등록")
    class 댓글_등록 {

        @Test
        @DisplayName("성공")
        void 성공() throws Exception {
            // given
            ArticleCommentAddReqDto commentAddReqDto = getCommentAddReqDto();

            String jsonContent = objectMapper.writeValueAsString(commentAddReqDto);

            // when & then
            ResultActions actions = mockMvc.perform(post("/api/articles/{articleId}/comments", articleId)
                    .contentType("application/json")
                    .content(jsonContent));

            actions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").value(ResponseText.SUCCESS_ADD_ARTICLE_COMMENT));

            then(articleCommentService).should().addArticleComment(any(), any(), any());
        }

        private ArticleCommentAddReqDto getCommentAddReqDto() {
            return ArticleCommentAddReqDto.builder()
                    .content("댓글입니다.")
                    .build();
        }
    }

    @Nested
    @DisplayName("댓글 수정")
    class 댓글_수정 {

        @Test
        @DisplayName("성공")
        void 성공() throws Exception {
            // given
            ArticleCommentModifyReqDto commentModifyReqDto = getCommentModifyReqDto();

            String jsonContent = objectMapper.writeValueAsString(commentModifyReqDto);

            // when & then
            ResultActions actions = mockMvc.perform(put("/api/articles/{articleId}/comments/{commentId}", articleId, commentId)
                    .contentType("application/json")
                    .content(jsonContent));

            actions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").value(ResponseText.SUCCESS_MODIFY_ARTICLE_COMMENT));

            then(articleCommentService).should().modifyArticleComment(any(), any());
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 댓글 ID")
        void 실패_존재하지_않는_댓글_ID() throws Exception {
            // given
            ArticleCommentModifyReqDto commentModifyReqDto = getCommentModifyReqDto();
            String jsonContent = objectMapper.writeValueAsString(commentModifyReqDto);

            willThrow(new CustomException(ErrorCode.ARTICLE_COMMENT_NOT_FOUND)).given(articleCommentService).modifyArticleComment(any(), any());

            // when & then
            ResultActions actions = mockMvc.perform(put("/api/articles/{articleId}/comments/{commentId}", articleId, commentId)
                    .contentType("application/json")
                    .content(jsonContent));

            actions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(ErrorCode.ARTICLE_COMMENT_NOT_FOUND.getMessage()));

            then(articleCommentService).should().modifyArticleComment(any(), any());
        }

        private ArticleCommentModifyReqDto getCommentModifyReqDto() {
            return ArticleCommentModifyReqDto.builder()
                    .content("댓글입니다.")
                    .build();
        }
    }

    @Nested
    @DisplayName("댓글 삭제")
    class 댓글_삭제 {

        @Test
        @DisplayName("성공")
        void 성공() throws Exception {
            // when & then
            ResultActions actions = mockMvc.perform(delete("/api/articles/{articleId}/comments/{commentId}", articleId, commentId)
                    .contentType("application/json"));

            actions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").value(ResponseText.SUCCESS_REMOVE_ARTICLE_COMMENT));

            then(articleCommentService).should().removeArticleComment(commentId);
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 댓글 ID")
        void 실패_존재하지_않는_댓글_ID() throws Exception {
            // given
            willThrow(new CustomException(ErrorCode.ARTICLE_COMMENT_NOT_FOUND)).given(articleCommentService).removeArticleComment(commentId);

            // when & then
            ResultActions actions = mockMvc.perform(delete("/api/articles/{articleId}/comments/{commentId}", articleId, commentId)
                    .contentType("application/json"));

            actions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(ErrorCode.ARTICLE_COMMENT_NOT_FOUND.getMessage()));

            then(articleCommentService).should().removeArticleComment(commentId);

        }
    }


}