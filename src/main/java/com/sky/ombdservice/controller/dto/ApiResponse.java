package com.sky.ombdservice.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean b, String message, T data) {
        this.success=b;
        this.message=message;
        this.data= data;
    }
    public ApiResponse() {
    }
}

