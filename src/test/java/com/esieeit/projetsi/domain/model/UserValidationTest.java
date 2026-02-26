package com.esieeit.projetsi.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

class UserValidationTest {

    @Test
    void shouldFail_whenEmailIsInvalid() {
        assertThrows(ValidationException.class,
                () -> new User("invalid-email", "alice", "temporary-hash", UserRole.USER));
    }

    @Test
    void shouldDefaultToUser_whenRoleIsNull() {
        User user = new User("alice@example.com", "alice", "temporary-hash", null);
        assertEquals(UserRole.USER, user.getRole());
    }

    @Test
    void shouldPass_whenRoleIsValid() {
        User user = new User("alice@example.com", "alice", "temporary-hash", UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, user.getRole());
    }
}
