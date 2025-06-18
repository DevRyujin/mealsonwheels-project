package com.merrymeal.mealsonwheels_backend.exception;

public class AccountNotApprovedException extends RuntimeException {
    public AccountNotApprovedException(String message) {
        super(message);
    }
}
