package org.example.otomotoclon.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectExistInDBException extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(ObjectDontExistInDBException.class);
    public ObjectExistInDBException(String message) {
        super(message);
        logger.error(message);
    }

    public ObjectExistInDBException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }

    public ObjectExistInDBException(Throwable cause) {
        super(cause);
    }

}
