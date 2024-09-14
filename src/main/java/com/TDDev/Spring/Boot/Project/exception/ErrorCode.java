package com.TDDev.Spring.Boot.Project.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception!"),
    USER_EXISTED(1001, "User existed!"),
    MIN_INVALID_PASSWORD(1002, "Password must be at least 6 characters!"),
    INVALID_KEY(1003, "Invalid key of ErrorCode"),
    USER_NOT_FOUND(1004, "User not found!"),
    INVALID_AUTH(1005, "Username or password is invalid!")
    ;
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
