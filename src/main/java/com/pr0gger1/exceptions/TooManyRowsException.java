package com.pr0gger1.exceptions;

public class TooManyRowsException extends Exception {
    public TooManyRowsException(String message) {
        super(message);
    }

    public TooManyRowsException() {
        super();
    }
}
