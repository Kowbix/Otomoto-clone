package org.example.otomotoclon.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectDontExistInDBException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(ObjectDontExistInDBException.class);

    public ObjectDontExistInDBException(String message) {
        super(message);
        logger.error(message);
    }

    public ObjectDontExistInDBException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }

    public ObjectDontExistInDBException(Throwable cause) {
        super(cause);
    }
}
