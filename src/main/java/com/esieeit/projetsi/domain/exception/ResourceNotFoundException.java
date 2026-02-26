package com.esieeit.projetsi.domain.exception;

/**
 * Thrown when a requested resource cannot be found.
 */
public class ResourceNotFoundException extends DomainException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
