package com.library.exception;

public class CustomIllegalArgumentException extends RuntimeException {

    public CustomIllegalArgumentException() {
        super();
    }

    public CustomIllegalArgumentException(String message) {
        super(message);
    }

    public CustomIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomIllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
