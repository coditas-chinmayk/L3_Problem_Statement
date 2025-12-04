package com.example.L3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {
    private Boolean success;
    private String message;
    private T data;
//    private HttpStatus status;    old jackson cannot read
    private HttpStatus status;

    public static <T> ApiResponseDto<T> ok(String message,T data){
        return ApiResponseDto.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }
    public static <T> ApiResponseDto<T> ok(String message){
        return ApiResponseDto.<T>builder()
                .success(true)
                .message(message)
                .build();
    }

    public static <T> ApiResponseDto<T> error(String message, HttpStatus status){
        return ApiResponseDto.<T>builder()
                .success(false)
                .message(message)
                .status(status)
                .build();
    }
}
