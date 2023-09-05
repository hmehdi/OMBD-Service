package com.sky.ombdservice.service;

import com.sky.ombdservice.models.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApiResponseService {

    public  <T> ResponseEntity<ApiResponse<T>> buildResponse(boolean success, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    public <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(boolean success, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
