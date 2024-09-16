package com.TDDev.Spring.Boot.Project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception!", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User existed!", HttpStatus.BAD_REQUEST),
    MIN_INVALID_PASSWORD(1002, "Password must be at least 6 characters!", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1003, "Invalid key of ErrorCode", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1005, "Username or password is invalid!", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1006, "You don't have permission!", HttpStatus.FORBIDDEN)
    ;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
