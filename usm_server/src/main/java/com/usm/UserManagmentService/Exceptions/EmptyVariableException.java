package com.usm.UserManagmentService.Exceptions;

/**
 * > This class is a custom exception class that extends the RuntimeException class
 */
public class EmptyVariableException extends RuntimeException {

    public EmptyVariableException() {
    }

    public EmptyVariableException(String message) {
        super(message);
    }

    public EmptyVariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyVariableException(Throwable cause) {
        super(cause);
    }

    public EmptyVariableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
