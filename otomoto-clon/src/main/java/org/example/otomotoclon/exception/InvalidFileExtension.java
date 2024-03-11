package org.example.otomotoclon.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidFileExtension extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(ObjectDontExistInDBException.class);

    public InvalidFileExtension(String message) {
        super(message);
        logger.error(message);
    }

    public InvalidFileExtension(String message, Throwable cause) {
        super(message, cause);
        logger.error(message);
    }

    public InvalidFileExtension(Throwable cause) {
        super(cause);
    }
}
