package com.example.happy_community_back.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ARTICLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "ATL001", "존재하지 않는 게시글입니다.");

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;
}
