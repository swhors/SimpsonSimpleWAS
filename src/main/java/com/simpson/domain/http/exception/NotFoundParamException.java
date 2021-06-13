package com.simpson.domain.http.exception;

public class NotFoundParamException extends RuntimeException {
    public NotFoundParamException(String message) {
        super(message);
    }
}
