package main.java.com.esieeit.projetsi.domain.validation;

import java.util.regex.Pattern;
import com.esiee.project.domain.exception.ValidationException;

public final class Validators {

    // Une regex (expression régulière) simple pour valider le format de l'email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private Validators() {
        // classe utilitaire, non instanciable
    }

    public static void requireNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException(fieldName + " ne doit pas être null");
        }
    }

    public static String requireNonBlank(String value, String fieldName, int min, int max) {
        if (value == null) {
            throw new ValidationException(fieldName + " ne doit pas être null");
        }
        String v = value.trim();
        if (v.isEmpty()) {
            throw new ValidationException(fieldName + " ne doit pas être vide");
        }
        return requireSize(v, fieldName, min, max);
    }

    public static String requireSize(String value, String fieldName, int min, int max) {
        int len = value.length();
        if (len < min || len > max) {
            throw new ValidationException(fieldName + " doit être entre " + min + " et " + max + " caractères");
        }
        return value;
    }

    public static String requireEmail(String value, String fieldName) {
        // Un email ne doit pas être vide et sa taille est généralement entre 5 et 254 caractères
        String v = requireNonBlank(value, fieldName, 5, 254);
        if (!EMAIL_PATTERN.matcher(v).matches()) {
            throw new ValidationException(fieldName + " doit être un email valide");
        }
        return v;
    }
}