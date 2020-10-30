package com.ra.course.stackoverflow.exception;

public class QuestionStatusException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public QuestionStatusException(final String message) {
        super(message);
    }
}
