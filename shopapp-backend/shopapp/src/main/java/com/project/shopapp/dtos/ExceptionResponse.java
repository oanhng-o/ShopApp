package com.project.shopapp.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
    private int statusCode;
    private String error;
    private String message;
}
