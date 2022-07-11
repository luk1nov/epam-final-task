package by.lukyanov.finaltask.validation.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorImplTest {
    private static final ValidatorImpl validator = ValidatorImpl.getInstance();

    @Test
    void isOneWordShouldReturnTrue() {
        assertTrue(validator.isOneWord("one"));
    }

    @Test
    void isOneWordShouldReturnFalse() {
        assertFalse(validator.isOneWord("one two"));
    }

    @Test
    void isValidSurnameShouldReturnTrue() {
        assertTrue(validator.isValidSurname("one"));
    }

    @Test
    void isValidSurnameShouldReturnFalse() {
        assertFalse(validator.isValidSurname("one two"));
    }

    @Test
    void isValidPasswordShouldReturnTrue() {
        assertTrue(validator.isValidPassword("123456Ab"));
    }

    @Test
    void isValidPasswordShouldReturnFalse() {
        assertFalse(validator.isValidPassword("12"));
    }

    @Test
    void isValidEmailShouldReturnTrue() {
        assertTrue(validator.isValidEmail("abv@gmail.com"));
    }

    @Test
    void isValidEmailShouldReturnFalse() {
        assertFalse(validator.isValidEmail("abvmail.com"));
    }

    @Test
    void isValidPhoneShouldReturnTrue() {
        assertTrue(validator.isValidPhone("29-123-12-23"));
    }

    @Test
    void isValidPhoneShouldReturnFalse() {
        assertFalse(validator.isValidPhone("29123-12-23"));
    }

    @Test
    void isValidIdShouldReturnTrue() {
        assertTrue(validator.isValidId("12"));
    }

    @Test
    void isValidIdShouldReturnFalse() {
        assertFalse(validator.isValidId("ab"));
    }

    @Test
    void isValidCarModelShouldReturnTrue() {
        assertTrue(validator.isValidCarModel("BMW"));
    }

    @Test
    void isValidCarModelShouldReturnFalse() {
        assertFalse(validator.isValidCarModel("BMW_1243 af"));
    }

    @Test
    void isValidVinCodeShouldReturnTrue() {
        assertTrue(validator.isValidVinCode("A8UFBAW6PN123AERK"));
    }

    @Test
    void isValidVinCodeShouldReturnFalse() {
        assertFalse(validator.isValidVinCode("A8UQBAW6PO123IERK"));
    }

    @Test
    void isValidPriceShouldReturnTrue() {
        assertTrue(validator.isValidPrice("400"));
    }

    @Test
    void isValidPriceShouldReturnFalse() {
        assertFalse(validator.isValidPrice("400.123"));
    }

    @Test
    void isValidAccelerationShouldReturnTrue() {
        assertTrue(validator.isValidAcceleration("3.3"));
    }

    @Test
    void isValidAccelerationShouldReturnFalse() {
        assertFalse(validator.isValidAcceleration("3.345"));
    }

    @Test
    void isValidPowerShouldReturnTrue() {
        assertTrue(validator.isValidPower("300"));
    }

    @Test
    void isValidPowerShouldReturnFalse() {
        assertFalse(validator.isValidPower("20"));
    }

    @Test
    void isLocaleExistsShouldReturnTrue() {
        assertTrue(validator.isLocaleExists("en"));
    }

    @Test
    void isLocaleExistsShouldReturnFalse() {
        assertFalse(validator.isLocaleExists("fr"));
    }

    @Test
    void isValidDateRangeShouldReturnTrue() {
        assertTrue(validator.isValidDateRange("2022-01-01"));
    }

    @Test
    void isValidDateRangeShouldReturnFalse() {
        assertFalse(validator.isValidDateRange("2022-01-01202"));
    }

    @Test
    void isValidMessageShouldReturnTrue() {
        assertTrue(validator.isValidMessage("Hello, welcome to los pollos hermanos."));
    }

    @Test
    void isValidMessageShouldReturnFalse() {
        assertFalse(validator.isValidMessage("Hello, welcome to !@#%los pollos hermanos."));
    }

    @Test
    void isValidNumberShouldReturnTrue() {
        assertTrue(validator.isValidNumber("123"));
    }

    @Test
    void isValidNumberShouldReturnFalse() {
        assertFalse(validator.isValidNumber("hgf"));
    }

    @Test
    void isValidSearchPatternShouldReturnTrue() {
        assertTrue(validator.isValidSearchQuery("audi"));
    }

    @Test
    void isValidSearchPatternShouldReturnFalse() {
        assertFalse(validator.isValidSearchQuery("Hello, welcome to los pollos hermanos, my name is Gustavo."));
    }
}