package com.project.shopapp.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private int statusCode;
    private String error;
    private String message;
}
