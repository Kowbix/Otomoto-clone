package org.example.otomotoclon.exception;

public class ObjectDontExistInDBException extends RuntimeException {

    public ObjectDontExistInDBException(String message) {
        super(message);
    }

    public ObjectDontExistInDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectDontExistInDBException(Throwable cause) {
        super(cause);
    }
}
