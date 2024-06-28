package com.example.happy_community_back.domain.Board.service;

import com.example.happy_community_back.domain.Board.dto.response.ArticleResDto;
import com.example.happy_community_back.domain.Board.entity.Article;
import com.example.happy_community_back.domain.Board.repository.ArticleRepository;
import com.example.happy_community_back.global.exception.CustomException;
import com.example.happy_community_back.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleAddReqDto;
import static com.example.happy_community_back.domain.Board.dto.request.ArticleReqDto.ArticleModifyReqDto;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @Nested
    @DisplayName("게시글 목록 조회")
    class 게시글_목록_조회 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            Article article = Article.builder()
                    .title("제목")
                    .content("내용입니다.")
                    .image(null)
                    .build();

            given(articleRepository.findAll()).willReturn(Collections.singletonList(article));

            // when
            List<ArticleResDto> result = articleService.getArticles();

            // then
            assertThat(result).isNotNull();
            then(articleRepository).should().findAll();
        }
    }

    @Nested
    @DisplayName("게시글 상세 조회")
    class 게시글_상세_조회 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            Article article = Article.builder()
                    .title("제목")
                    .content("내용입니다.")
                    .image(null)
                    .build();

            given(articleRepository.findById(1L)).willReturn(Optional.of(article));

            // when
            ArticleResDto result = articleService.getArticle(1L);

            // then
            assertThat(result).isNotNull();
            then(articleRepository).should().findById(1L);
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 게시글 ID")
        void 실패_존재하지_않는_게시글_ID() {
            // given
            given(articleRepository.findById(1L)).willReturn(Optional.empty());

            // when
            assertThatThrownBy(() -> articleService.getArticle(1L))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ARTICLE_NOT_FOUND);

            // then
            then(articleRepository).should().findById(1L);
        }
    }

    @Nested
    @DisplayName("게시글 등록")
    class 게시글_등록 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            ArticleAddReqDto articleAddReqDto = ArticleAddReqDto.builder()
                    .title("제목")
                    .content("내용입니다.")
                    .image(null)
                    .build();

            // when
            articleService.addArticle(articleAddReqDto);

            // then
            then(articleRepository).should().save(any());
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class 게시글_수정 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            ArticleModifyReqDto articleModifyReqDto = ArticleModifyReqDto.builder()
                    .articleId(1L)
                    .title("제목")
                    .content("내용입니다.")
                    .image(null)
                    .build();

            Article article = Article.builder()
                    .title("재목")
                    .content("내용입니다.")
                    .image(null)
                    .build();

            given(articleRepository.findById(1L)).willReturn(Optional.of(article));

            // when
            articleService.modifyArticle(articleModifyReqDto);

            // then
            then(articleRepository).should().findById(1L);
            then(articleRepository).should().save(any());
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 게시글 ID")
        void 실패_존재하지_않는_게시글_ID() {
            // given
            ArticleModifyReqDto articleModifyReqDto = ArticleModifyReqDto.builder()
                    .articleId(1L)
                    .title("제목")
                    .content("내용입니다.")
                    .image(null)
                    .build();

            given(articleRepository.findById(1L)).willReturn(Optional.empty());

            // when
            assertThatThrownBy(() -> articleService.modifyArticle(articleModifyReqDto))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ARTICLE_NOT_FOUND);

            // then
            then(articleRepository).should().findById(1L);
        }
    }

    @Nested
    @DisplayName("게시글 삭제")
    class 게시글_삭제 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            Article article = Article.builder()
                    .title("재목")
                    .content("내용입니다.")
                    .image(null)
                    .build();

            given(articleRepository.findById(1L)).willReturn(Optional.of(article));

            // when
            articleService.removeArticle(1L);

            // then
            then(articleRepository).should().findById(1L);
            then(articleRepository).should().save(any());

        }

        @Test
        @DisplayName("실패 - 존재하지 않는 게시글 ID")
        void 실패_존재하지_않는_게시글_ID() {
            // given
            given(articleRepository.findById(1L)).willReturn(Optional.empty());

            // when
            assertThatThrownBy(() -> articleService.removeArticle(1L))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ARTICLE_NOT_FOUND);

            // then
            then(articleRepository).should().findById(1L);
        }
    }
}