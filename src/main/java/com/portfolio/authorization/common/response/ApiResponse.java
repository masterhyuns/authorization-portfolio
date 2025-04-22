package com.portfolio.authorization.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolio.authorization.common.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @param <T> 응답 데이터 타입 (data)
 */
public record ApiResponse<T>(
        int status,
        String code,
        T data
) {
    /**
     * 성공 응답 반환 (data 없음)
     */
    public static ResponseEntity<ApiResponse<Void>> OK() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "success", null));
    }

    /**
     * 성공 응답 반환 (data 있음)
     */
    public static <T> ResponseEntity<ApiResponse<T>> OK(T data) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "success", data));
    }

    /**
     * 실패 응답 반환
     */
    public static ResponseEntity<ApiResponse<Void>> ERROR(ApiResponseCode code) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(code.getStatus().value(), code.getCode(), null));
    }

    /**
     * 실패 응답 (예외 기반)
     */
    public static ResponseEntity<ApiResponse<Void>> ERROR(CustomException ex) {
        ApiResponseCode code = ex.getCode();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(code.getStatus().value(), code.getCode(), null));
    }
}
