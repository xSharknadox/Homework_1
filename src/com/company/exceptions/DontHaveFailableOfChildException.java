package com.company.exceptions;

public class DontHaveFailableOfChildException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Don't have a Failable of child.";
    }
}
