package com.thardal.bankapp.global.util;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtil {

    public static Long getRandomNumber(int charCount){
        String randomNumeric = RandomStringUtils.randomNumeric(charCount);

        return Long.valueOf(randomNumeric);
    }

    public static String getRandomString(int charCount){
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(charCount);

        return randomAlphabetic;
    }

    public static String getRandomNumberAsString(int charCount){
        String randomNumeric = RandomStringUtils.randomNumeric(charCount);

        return randomNumeric;
    }
}
