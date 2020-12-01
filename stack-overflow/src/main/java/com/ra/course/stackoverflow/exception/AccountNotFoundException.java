package com.ra.course.stackoverflow.exception;

public class AccountNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public AccountNotFoundException(final String message) {
        super(message);
    }
}
