package com.ra.course.stackoverflow.exception;

public class CommentNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public CommentNotFoundException(final String message) {
        super(message);
    }
}
