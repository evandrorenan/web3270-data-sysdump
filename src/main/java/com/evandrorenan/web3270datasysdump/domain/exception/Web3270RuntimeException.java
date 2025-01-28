package com.evandrorenan.web3270datasysdump.domain.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom runtime exception for the Web3270 Data Sysdump application.
 * This exception is thrown when application-specific runtime errors occur during
 * the processing of 3270 terminal system dumps.
 *
 * @author Evandro Renan
 * @version 0.0.1-SNAPSHOT
 */
@Slf4j
public class Web3270RuntimeException extends RuntimeException {

    /**
     * Constructs a new Web3270RuntimeException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public Web3270RuntimeException(String message) {
        super(message);
        log.error(message);
    }

    /**
     * Constructs a new Web3270RuntimeException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public Web3270RuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
