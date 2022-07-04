package by.lukyanov.finaltask.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {
    private static final String plainPassword = "qwertY123";
    private static final PasswordEncoder encoder = PasswordEncoder.getInstance();

    @Test
    void encode() {
        assertThat(encoder.encode(plainPassword)).hasSize(96);
    }

    @Test
    void verifyShouldReturnTrue() {
        String hashPassword = encoder.encode(plainPassword);
        assertTrue(encoder.verify(hashPassword, plainPassword));
    }

    @Test
    void verifyShouldReturnFalse() {
        String wrongPassword = "123";
        String hashPassword = encoder.encode(plainPassword);
        assertFalse(encoder.verify(hashPassword, wrongPassword));
    }
}