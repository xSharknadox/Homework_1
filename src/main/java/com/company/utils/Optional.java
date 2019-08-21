package com.company.utils;

public class Optional<T> {
    private static final Optional<?> EMPTY = new Optional<>();
    private final T value;

    public Optional() {
        this.value = null;
    }

    public Optional(T value) {
        this.value = value;
    }

    public static <T> Optional<T> empty() {
        return (Optional<T>) EMPTY;
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        return value;
    }
}
