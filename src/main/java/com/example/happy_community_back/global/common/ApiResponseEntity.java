package com.example.happy_community_back.global.common;

public record ApiResponseEntity<T>(int status, T data) {
    public static <T> ApiResponseEntity<T> of(T data) {
        return new ApiResponseEntity<>(200, data);
    }
}
