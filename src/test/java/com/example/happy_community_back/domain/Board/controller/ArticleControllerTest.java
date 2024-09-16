package com.example.happy_community_back.domain.Board.controller;

import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleAddReqDto;
import com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleModifyReqDto;
import com.example.happy_community_back.domain.Board.dto.response.ArticleResDto;
import com.example.happy_community_back.domain.Board.service.ArticleService;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ArticleController.class)
@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @MockBean
    private ArticleService articleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private final long articleId = 1L;

    @Nested
    @DisplayName("게시글 목록 조회")
    class 게시글_목록_조회 {

        @Test
        @DisplayName("성공")
        void 성공() throws Exception {
            // given
            List<ArticleResDto> articles = Collections.singletonList(getArticleResDto());
            given(articleService.getArticles()).willReturn(articles);

            // when & then
            ResultActions actions = mockMvc.perform(get("/api/articles")
                    .contentType("application/json"));

            actions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$[0].articleId").value(articleId))
                    .andExpect(jsonPath("$[0].title").value("제목"))
                    .andExpect(jsonPath("$[0].content").value("내용입니다."));

            then(articleService).should().getArticles();
        }

        private ArticleResDto getArticleResDto() {
            return ArticleResDto.builder()
                    .articleId(1L)
                    .title("제목")
                    .content("내용입니다.")
                    .build();
        }
    }

    @Nested
    @DisplayName("게시글 상제 조회")
    class 게시글_상세_조회 {

        @Test
        @DisplayName("성공")
        void 성공() throws Exception {

            // given
            ArticleResDto resDto = getArticleResDto();

            given(articleService.getArticle(articleId)).willReturn(resDto);

            // when & then
            ResultActions actions = mockMvc.perform(get("/api/articles/{articleId}", articleId)
                    .contentType("application/json"));

            actions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.articleId").value(articleId))
                    .andExpect(jsonPath("$.title").value("제목"))
                    .andExpect(jsonPath("$.content").value("내용입니다."));

            then(articleService).should().getArticle(articleId);
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 게시글 ID")
        void 실패_존재하지_않는_게시글_ID() throws Exception {

            // given
            given(articleService.getArticle(articleId)).willThrow(new CustomException(ErrorCode.ARTICLE_NOT_FOUND));

            // when & then
            ResultActions actions = mockMvc.perform(get("/api/articles/{articleId}", articleId)
                    .contentType("application/json"));

            actions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(ErrorCode.ARTICLE_NOT_FOUND.getMessage()));

            then(articleService).should().getArticle(articleId);
        }

        private ArticleResDto getArticleResDto() {
            return ArticleResDto.builder()
                    .articleId(1L)
                    .title("제목")
                    .content("내용입니다.")
                    .image(null)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Nested
    @DisplayName("게시글 등록")
    class 게시글_등록 {

        @Test
        @DisplayName("성공")
        void 성공() throws Exception {
            // given
            ArticleAddReqDto articleAddReqDto = getArticleAddReqDto();

            String jsonContent = objectMapper.writeValueAsString(articleAddReqDto);

            // when & then
            ResultActions actions = mockMvc.perform(post("/api/articles")
                    .contentType("application/json")
                    .content(jsonContent));

            actions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").value(ResponseText.SUCCESS_ADD_ARTICLE));

            then(articleService).should().addArticle(any(), any());
        }

        private ArticleAddReqDto getArticleAddReqDto() {
            return ArticleAddReqDto.builder()
                    .title("제목")
                    .content("내용입니다.")
                    .image(null)
                    .build();
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class 게시글_수정 {

        @Test
        @DisplayName("성공")
        void 성공() throws Exception {
            // given
            ArticleModifyReqDto articleModifyReqDto = getArticleModifyReqDto();

            String jsonContent = objectMapper.writeValueAsString(articleModifyReqDto);

            // when & then
            ResultActions actions = mockMvc.perform(put("/api/articles/{articleId}", articleId)
                    .contentType("application/json")
                    .content(jsonContent));

            actions.andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").value(ResponseText.SUCCESS_MODIFY_ARTICLE));

            then(articleService).should().modifyArticle(1L, any());
        }

        private ArticleModifyReqDto getArticleModifyReqDto() {
            return ArticleModifyReqDto.builder()
                    .title("제목")
                    .content("내용입니다.")
                    .build();
        }

        @Nested
        @DisplayName("게시글 삭제")
        class 게시글_삭제 {

            @Test
            @DisplayName("성공")
            void 성공() throws Exception {
                // when & then
                ResultActions actions = mockMvc.perform(delete("/api/articles/{articleId}", articleId)
                        .contentType("application/json"));

                actions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.data").value(ResponseText.SUCCESS_REMOVE_ARTICLE));

                then(articleService).should().removeArticle(articleId);
            }

            @Test
            @DisplayName("실패 - 존재하지 않는 게시글 ID")
            void 실패_존재하지_않는_게시글_ID() throws Exception {
                // given
                willThrow(new CustomException(ErrorCode.ARTICLE_NOT_FOUND)).given(articleService).removeArticle(articleId);

                // when & then
                ResultActions actions = mockMvc.perform(delete("/api/articles/{articleId}", articleId)
                        .contentType("application/json"));

                actions
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value(ErrorCode.ARTICLE_NOT_FOUND.getMessage()));

                then(articleService).should().removeArticle(articleId);
            }
        }
    }
}