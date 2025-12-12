package com.histack.ltplatform.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class ResponseUtil {
	
	public<T>  ResponseEntity<ApiResponse<T>> setSuccessResponse(T data, String message) {
		
        return new ResponseEntity<>(ApiResponse.success(message, data),HttpStatus.OK); 
    }
	
	 public<T>  ResponseEntity<ApiResponse<T>> setErrorResponse(String message, HttpStatus status) {
	    	
    	return new ResponseEntity<>(ApiResponse.failure(message, null),status);
        
    }
	 
	 

}

