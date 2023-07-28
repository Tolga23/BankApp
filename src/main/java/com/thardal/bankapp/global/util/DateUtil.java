package com.thardal.bankapp.global.util;

import com.thardal.bankapp.global.enums.GlobalErrorMessages;
import com.thardal.bankapp.global.exceptions.BusinessException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static LocalDate convertToLocalDate(Date dateToConvert) {

        if (dateToConvert == null) {
            throw new BusinessException(GlobalErrorMessages.DATE_COULD_NOT_BE_CONVERTED);
        }

        LocalDate localDate = Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {

        if (dateToConvert == null) {
            throw new BusinessException(GlobalErrorMessages.DATE_COULD_NOT_BE_CONVERTED);
        }

        LocalDateTime localDateTime = dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return localDateTime;
    }

    public static Date convertToDate(LocalDate dateToConvert) {

        if (dateToConvert == null) {
            throw new BusinessException(GlobalErrorMessages.DATE_COULD_NOT_BE_CONVERTED);
        }

        Date from = Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        return from;
    }
}
