package com.ra.course.stackoverflow.exception;

public class QuestionNotFoundException extends RuntimeException{
    public static final long serialVersionUID = 1L;

    public QuestionNotFoundException(final String message) {
        super(message);
    }
}
