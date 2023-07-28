package com.thardal.bankapp.global.util;

import com.thardal.bankapp.global.enums.GlobalErrorMessages;
import com.thardal.bankapp.global.exceptions.BusinessException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

public class StringUtil {

    public static Long getRandomNumber(int charCount){
        negativeValueCheck(charCount);

        String randomNumeric;
        do {
            randomNumeric = RandomStringUtils.randomNumeric(charCount);
        }
        while (randomNumeric.startsWith("0"));


        Long randomNumber = null;
        if (StringUtils.hasText(randomNumeric) ) {
            randomNumber = Long.valueOf(randomNumeric);
        }

        return randomNumber;
    }

    public static String getRandomString(int charCount){
        negativeValueCheck(charCount);

        String randomAlphabetic = RandomStringUtils.randomAlphabetic(charCount);

        return randomAlphabetic;
    }

    public static String getRandomNumberAsString(int charCount){
        negativeValueCheck(charCount);

        String randomNumeric = RandomStringUtils.randomNumeric(charCount);

        return randomNumeric;
    }

    private static void negativeValueCheck(int charCount) {
        if (charCount < 0) throw new BusinessException(GlobalErrorMessages.VALUE_COULD_NOT_BE_NEGATIVE);
    }
}
