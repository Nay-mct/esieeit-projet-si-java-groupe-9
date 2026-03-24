package com.esieeit.projetsi.domain.exception;

/**
 * Thrown when authentication fails or access is denied for a caller.
 */
public class UnauthorizedException extends DomainException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
