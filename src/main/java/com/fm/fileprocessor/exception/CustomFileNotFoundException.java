package com.fm.fileprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
//comment 1
////use exception Handler.
public class CustomFileNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 5407848424326359236L;

    public CustomFileNotFoundException(String message) {
        super(message);
    }

    public CustomFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
