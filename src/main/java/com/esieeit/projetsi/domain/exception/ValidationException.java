package com.esieeit.projetsi.domain.exception;

/**
 * Thrown when an entity or value violates validation rules.
 */
public class ValidationException extends DomainException {

    private final String fieldName;

    public ValidationException(String message) {
        super(message);
        this.fieldName = null;
    }

    public ValidationException(String fieldName, String message) {
        super(fieldName + " : " + message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
