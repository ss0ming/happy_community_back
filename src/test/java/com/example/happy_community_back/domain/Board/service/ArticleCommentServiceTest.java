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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleCommentService articleCommentService;

    @Nested
    @DisplayName("댓글 목록 조회")
    class 댓글_목록_조회 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            ArticleComment comment = ArticleComment.builder()
                    .content("댓글입니다.")
                    .build();

            given(articleCommentRepository.findAllByArticleIdAndIsDeleted(1L, 'n')).willReturn(Collections.singletonList(comment));

            // when
            List<ArticleCommentResDto> result = articleCommentService.getArticleComments(1L);

            // then
            assertThat(result).isNotNull();
            then(articleCommentRepository).should().findAllByArticleIdAndIsDeleted(1L, 'n');
        }
    }

    @Nested
    @DisplayName("댓글 단일 조회")
    class 댓글_단일_조회 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            ArticleComment comment = ArticleComment.builder()
                    .content("댓글입니다.")
                    .build();

            given(articleCommentRepository.findById(1L)).willReturn(Optional.of(comment));

            // when
            ArticleCommentResDto result = articleCommentService.getArticleComment(1L);

            // then
            assertThat(result).isNotNull();
            then(articleCommentRepository).should().findById(1L);
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 댓글 ID")
        void 실패_존재하지_않는_댓글_ID() {
            // given
            given(articleCommentRepository.findById(1L)).willReturn(Optional.empty());

            // when
            assertThatThrownBy(() -> articleCommentService.getArticleComment(1L))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ARTICLE_COMMENT_NOT_FOUND);

            // then
            then(articleCommentRepository).should().findById(1L);
        }
    }

    @Nested
    @DisplayName("댓글 등록")
    class 댓글_등록 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            Article article = Article.builder()
                    .title("제목")
                    .content("내용입니다.")
                    .image(null)
                    .build();

            ArticleCommentAddReqDto commentAddReqDto = ArticleCommentAddReqDto.builder()
                    .content("댓글입니다.")
                    .build();

            // when
            given(articleRepository.findById(1L)).willReturn(Optional.of(article));
            articleCommentService.addArticleComment(1L, commentAddReqDto, any());

            // then
            then(articleCommentRepository).should().save(any());
        }
    }

    @Nested
    @DisplayName("댓글 수정")
    class 댓글_수정 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            ArticleCommentModifyReqDto commentModifyReqDto = ArticleCommentModifyReqDto.builder()
                    .content("댓글입니다.")
                    .build();

            ArticleComment comment = ArticleComment.builder()
                    .content("댓글입니다.")
                    .build();

            given(articleCommentRepository.findById(1L)).willReturn(Optional.of(comment));

            // when
            articleCommentService.modifyArticleComment(1L, commentModifyReqDto);

            // then
            then(articleCommentRepository).should().findById(1L);
            then(articleCommentRepository).should().save(any());
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 댓글 ID")
        void 실패_존재하지_않는_댓글_ID() {
            // given
            ArticleCommentModifyReqDto commentModifyReqDto = ArticleCommentModifyReqDto.builder()
                    .content("댓글입니다.")
                    .build();

            given(articleCommentRepository.findById(1L)).willReturn(Optional.empty());

            // when
            assertThatThrownBy(() -> articleCommentService.modifyArticleComment(1L, commentModifyReqDto))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ARTICLE_COMMENT_NOT_FOUND);

            // then
            then(articleCommentRepository).should().findById(1L);
        }
    }

    @Nested
    @DisplayName("댓글 삭제")
    class 댓글_삭제 {

        @Test
        @DisplayName("성공")
        void 성공() {
            // given
            ArticleComment comment = ArticleComment.builder()
                    .content("댓글입니다.")
                    .build();

            given(articleCommentRepository.findById(1L)).willReturn(Optional.of(comment));

            // when
            articleCommentService.removeArticleComment(1L);

            // then
            then(articleCommentRepository).should().findById(1L);
            then(articleCommentRepository).should().save(any());
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 댓글 ID")
        void 실패_존재하지_않는_댓글_ID() {
            // given
            given(articleCommentRepository.findById(1L)).willReturn(Optional.empty());

            // when
            assertThatThrownBy(() -> articleCommentService.removeArticleComment(1L))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ARTICLE_COMMENT_NOT_FOUND);

            // then
            then(articleCommentRepository).should().findById(1L);
        }
    }
}