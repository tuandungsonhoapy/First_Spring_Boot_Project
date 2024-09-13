package com.TDDev.Spring.Boot.Project.exception;

import com.TDDev.Spring.Boot.Project.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleRuntimeException(Exception exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleRuntimeException(AppException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(exception.getErrorCode().getMessage());
        apiResponse.setCode(exception.getErrorCode().getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ApiResponse apiResponse = new ApiResponse();
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try{
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (Exception exception1){

        }
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
