package com.esieeit.projetsi.domain.model;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidationTest {

    @Test
    void shouldFail_whenEmailIsInvalid() {
        assertThrows(ValidationException.class,
                () -> new User("invalid-email", "alice", Set.of(UserRole.USER)));
    }

    @Test
    void shouldFail_whenRolesAreEmpty() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> new User("alice@example.com", "alice", Collections.emptySet()));

        assertTrue(exception.getMessage().contains("user.roles"));
    }
}