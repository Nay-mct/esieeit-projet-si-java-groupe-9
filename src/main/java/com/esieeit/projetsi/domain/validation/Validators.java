package com.esieeit.projetsi.domain.validation;

import com.esieeit.projetsi.domain.exception.ValidationException;
import java.util.regex.Pattern;

/**
 * Utility class containing reusable domain validation helpers.
 */
public final class Validators {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private Validators() {
    }

    public static void requireNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException(fieldName, "must not be null");
        }
    }

    public static String requireNonBlank(String value, String fieldName, int min, int max) {
        if (value == null) {
            throw new ValidationException(fieldName, "must not be null");
        }
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            throw new ValidationException(fieldName, "must not be blank");
        }
        return requireSize(normalized, fieldName, min, max);
    }

    public static String requireSize(String value, String fieldName, int min, int max) {
        requireNonNull(value, fieldName);
        int length = value.length();
        if (length < min || length > max) {
            throw new ValidationException(fieldName, "length must be between " + min + " and " + max);
        }
        return value;
    }

    public static String requireEmail(String value, String fieldName) {
        String email = requireNonBlank(value, fieldName, 5, 254);
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException(fieldName, "must be a valid email");
        }
        return email;
    }

    public static Long requirePositive(Long value, String fieldName) {
        requireNonNull(value, fieldName);
        if (value <= 0) {
            throw new ValidationException(fieldName, "must be greater than 0");
        }
        return value;
    }
}
