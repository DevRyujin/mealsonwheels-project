package com.merrymeal.mealsonwheels.exception;

import com.merrymeal.mealsonwheels.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundHandler(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotApprovedException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotApprovedException(AccountNotApprovedException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT); // 409
    }

    //@ExceptionHandler(Exception.class)
    //public ResponseEntity<ErrorResponse> generalExceptionHandler(Exception ex) {
    //    return new ResponseEntity<>(new ErrorResponse("An error occurred: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    //}

    // Debug
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace(); // shows the exact cause in the terminal
        return ResponseEntity.status(500).body(Map.of(
                "status", 500,
                "error", ex.getMessage(),
                "message", "Something went wrong. Please try again."
        ));
    }


}
