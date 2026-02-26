package com.esieeit.projetsi.domain.exception;

/**
 * Thrown when a request carries logically invalid data.
 */
public class InvalidDataException extends DomainException {

    public InvalidDataException(String message) {
        super(message);
    }
}
