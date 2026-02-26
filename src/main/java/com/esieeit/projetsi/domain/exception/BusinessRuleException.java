package com.esieeit.projetsi.domain.exception;

/**
 * Thrown when a business workflow rule is violated.
 */
public class BusinessRuleException extends DomainException {

    public BusinessRuleException(String message) {
        super(message);
    }
}
