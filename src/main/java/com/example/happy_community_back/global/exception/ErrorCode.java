package com.example.happy_community_back.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 회원 관련 에러코드
    EXIST_USER_ID(HttpStatus.BAD_REQUEST, "UA001", "이미 존재하는 아이디입니다."),
    NOT_EXIST_USER_ID(HttpStatus.BAD_REQUEST, "UA002", "존재하지 않는 아이디입니다."),
    MISMATCH_PASSWORD_AND_PASSWORD_CONFIRM(HttpStatus.BAD_REQUEST, "UA003", "비밀번호가 일치하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "UA004", "만료된 엑세스 토큰입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "UA005", "유효하지 않은 엑세스 토큰입니다."),
    FAILED_TO_LOGIN(HttpStatus.BAD_REQUEST, "UA006", "로그인에 실패하였습니다. 아이디 또는 비밀번호를 확인해주세요."),
    INCORRECT_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "UA007", "비밀번호는 8자 이상 20자 이하로 입력해주세요."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AU008", "접근 권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UA009", "로그인이 필요합니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "UA010", "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "UA011", "이미 존재하는 닉네임입니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "UA012", "비밀번호가 일치하지 않습니다."),
    FILE_READ_ERROR(HttpStatus.BAD_REQUEST, "UA013", "파일 읽기 오류입니다."),

    // 게시글 관련 에러코드
    ARTICLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "ATL001", "존재하지 않는 게시글입니다."),
    ARTICLE_COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "ATL002", "존재하지 않는 댓글입니다."),

    // 서버 오류
    SEVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SV001", "서버 오류");

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;
}
