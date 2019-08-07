package com.company.exceptions;

public class DontHaveFailableOfChildException extends RuntimeException {
    private String message;

    public DontHaveFailableOfChildException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Don't have a Failable of child. " + message;
    }
}
