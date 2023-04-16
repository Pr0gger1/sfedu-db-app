package com.pr0gger1.app.ConsoleTable.exceptions;

public class TooManyRowsException extends Exception {
    public TooManyRowsException(String message) {
        super(message);
    }

    public TooManyRowsException() {
        super();
    }
}
