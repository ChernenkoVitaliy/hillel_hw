package com.ra.course.stackoverflow.exception;

public class AnswerNotFoundException extends RuntimeException{
    public static final long serialVersionUID = 1L;

    public AnswerNotFoundException(final String message) {
        super(message);
    }
}
