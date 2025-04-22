package com.portfolio.authorization.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ✅ 코드 정의용 Enum (HTTP status와 매핑되는 도메인 에러 코드)
 */
@Getter
public enum ApiResponseCode {
    SUCCESS(HttpStatus.OK, "success"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user_not_found"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "invalid_request"),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "invalid_code"),
    INVALID_REDIRECT_URL(HttpStatus.BAD_REQUEST, "invalid_redirect_url"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "invalid_token"),
    INVALID_GRANT_TYPE(HttpStatus.BAD_REQUEST, "unsupported_grant_type"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "forbidden"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "internal_error");

    private final HttpStatus status;
    private final String code;

    ApiResponseCode(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }
}
