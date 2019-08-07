package com.company.exceptions;

public class SizeException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Don't have a size. (Check children of object)";
    }
}
