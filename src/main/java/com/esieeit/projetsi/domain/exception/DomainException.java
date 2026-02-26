package com.esieeit.projetsi.domain.exception;

/**
 * Base exception for all domain-level errors.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
