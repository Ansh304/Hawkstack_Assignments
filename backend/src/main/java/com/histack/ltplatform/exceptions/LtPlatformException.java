package com.histack.ltplatform.exceptions;

public class LtPlatformException extends RuntimeException {

    public LtPlatformException(String message) {
        super(message);
    }

    public LtPlatformException(String message, Throwable cause) {
        super(message, cause);
    }
}
