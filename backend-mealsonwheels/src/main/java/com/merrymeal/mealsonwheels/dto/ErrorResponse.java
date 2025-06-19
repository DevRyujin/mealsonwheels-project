package com.merrymeal.mealsonwheels.dto;

import java.util.Date;
/**
 * ErrorResponse is a DTO class used to represent error responses in the application.
 * It contains a message and a timestamp indicating when the error occurred.
 */
public class ErrorResponse {
    private String message;
    private String timestamp;
    
    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = new Date().toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
