package com.ride.dto.response;

public class ApiResponse {

     public final String message;
    public final int status;

    public ApiResponse(String message, int status) {
       this.message = message;
        this.status = status;
    }
}
