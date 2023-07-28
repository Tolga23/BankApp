package com.thardal.bankapp.global.util;

import com.thardal.bankapp.global.enums.GlobalErrorMessages;
import com.thardal.bankapp.global.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void shouldGetRandomNumber() {
        int charCount = 5;

        Long randomNumber = StringUtil.getRandomNumber(charCount);

        assertEquals(charCount, randomNumber.toString().length());
    }

    @Test
    void shouldGetRandomNumberWhenCharSızeZero() {
        int charCount = 0;

        Long randomNumber = StringUtil.getRandomNumber(charCount);

        assertNull(randomNumber);
    }

    @Test
    void shouldGetRandomNumberWhenCharNegative() {
        int charCount = -1;

        BusinessException businessException = assertThrows(BusinessException.class, () -> StringUtil.getRandomNumber(charCount));

        assertEquals(GlobalErrorMessages.VALUE_COULD_NOT_BE_NEGATIVE, businessException.getBaseErrorMessages());

    }

    @Test
    void shouldGetRandomNumberWhenCharTooLong() {
        int charCount = 50;

        assertThrows(NumberFormatException.class, () -> StringUtil.getRandomNumber(charCount));
    }

    @Test
    void shouldGetRandomString() {
        int charCount = 5;

        String randomString = StringUtil.getRandomString(charCount);

        assertEquals(charCount, randomString.length());
    }

    @Test
    void shouldGetRandomStringWhenCharSızeZero() {
        int charCount = 0;

        String randomString = StringUtil.getRandomString(charCount);

        assertEquals(charCount, randomString.length());
    }

    @Test
    void shouldGetRandomNumberAsString() {
        int charCount = 5;

        String randomNumberAsString = StringUtil.getRandomNumberAsString(charCount);

        assertEquals(charCount, randomNumberAsString.length());
    }

    @Test
    void shouldGetRandomNumberAsStringWhenCharSızeZero() {
        int charCount = 0;

        String randomNumberAsString = StringUtil.getRandomNumberAsString(charCount);

        assertEquals(charCount, randomNumberAsString.length());
    }

    @Test
    void shouldGetRandomNumberAsStringWhenCharNegative() {
        int charCount = -1;

        BusinessException businessException = assertThrows(BusinessException.class, () -> StringUtil.getRandomNumberAsString(charCount));

        assertEquals(GlobalErrorMessages.VALUE_COULD_NOT_BE_NEGATIVE, businessException.getBaseErrorMessages());

    }
}