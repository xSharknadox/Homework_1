package com.company.exceptions;

public class ElementNotFoundException extends RuntimeException {
    private String elementName;

    public ElementNotFoundException(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public String getMessage() {
        return "Element not found: " + elementName;
    }
}
