package com.merrymeal.mealsonwheels.exception;

public class AccountNotApprovedException extends RuntimeException {
    public AccountNotApprovedException(String message) {
        super(message);
    }
}
