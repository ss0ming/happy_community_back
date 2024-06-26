package com.example.happy_community_back.global.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // 인스턴스화 방지
public class ResponseText {

    public static final String OK = "OK";

    public static final String SUCCESS_ADD_ARTICLE = "게시글 등록 성공";

    public static final String SUCCESS_MODIFY_ARTICLE = "게시글 수정 성공";

    public static final String SUCCESS_REMOVE_ARTICLE = "게시글 삭제 성공";
}
