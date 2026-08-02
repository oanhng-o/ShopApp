package com.project.shopapp.dtos;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    @JsonProperty("status_code")
    private int statusCode;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(message);
        response.setData(null);
        return response;
    }
}
