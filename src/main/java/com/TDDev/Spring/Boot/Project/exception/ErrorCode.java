package com.TDDev.Spring.Boot.Project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception!", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User existed!", HttpStatus.BAD_REQUEST),
    MIN_INVALID_PASSWORD(1002, "Password must be at least {min} characters!", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1003, "Invalid key of ErrorCode", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1005, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006, "You don't have permission!", HttpStatus.FORBIDDEN),
    SERVICE_UNAVAILABLE(1007, "Service is unavailable!", HttpStatus.SERVICE_UNAVAILABLE),
    PERMISSION_EXISTED(1008, "Permission existed!", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1009, "Role existed!", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1010, "Your age must be at least {min}!", HttpStatus.BAD_REQUEST)
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
