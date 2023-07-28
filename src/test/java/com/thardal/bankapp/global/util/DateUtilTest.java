package com.thardal.bankapp.global.util;

import com.thardal.bankapp.global.enums.GlobalErrorMessages;
import com.thardal.bankapp.global.exceptions.BusinessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateUtilTest {

    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleDateTimeFormat;

    @Test
    void shouldConvertToLocalDate() throws ParseException {

        Date date = simpleDateFormat.parse("01-01-2020");

        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(2020, localDate.getYear());
        assertEquals(1, localDate.getMonthValue());
        assertEquals(1, localDate.getDayOfMonth());
    }

    @Test
    void shouldNotConvertToLocalDateWhenParameterIsNull() {

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            DateUtil.convertToLocalDate(null);
        });

        assertEquals(businessException.getBaseErrorMessages(), GlobalErrorMessages.DATE_COULD_NOT_BE_CONVERTED);
    }

    @Test
    void shouldConvertToLocalDateWhen29Feb() throws ParseException {

        Date date = simpleDateFormat.parse("29-02-2024");

        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(2024, localDate.getYear());
        assertEquals(02, localDate.getMonthValue());
        assertEquals(29, localDate.getDayOfMonth());
    }

    @Test
    void shouldConvertToLocalWhen01Jan() throws ParseException {

        Date date = simpleDateFormat.parse("01-01-2020");

        LocalDate localDate = DateUtil.convertToLocalDate(date);

        assertEquals(2020, localDate.getYear());
        assertEquals(1, localDate.getMonthValue());
        assertEquals(1, localDate.getDayOfMonth());
    }

    @Test
    void shouldConvertToLocalDateTime() throws ParseException {

        Date date = simpleDateTimeFormat.parse("01-01-2020 12:00:00");

        LocalDateTime localDateTime = DateUtil.convertToLocalDateTime(date);

        assertEquals(2020, localDateTime.getYear());
        assertEquals(1, localDateTime.getMonthValue());
        assertEquals(1, localDateTime.getDayOfMonth());
        assertEquals(12, localDateTime.getHour());
        assertEquals(0, localDateTime.getMinute());
        assertEquals(0, localDateTime.getSecond());
    }

    @Test
    void shouldNotConvertToLocalDateTimeWhenParameterIsNull() {

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            DateUtil.convertToLocalDateTime(null);
        });

        assertEquals(businessException.getBaseErrorMessages(), GlobalErrorMessages.DATE_COULD_NOT_BE_CONVERTED);
    }

    @Test
    void shouldConvertToLocatlDateTimeWhenTimeIs00000() throws ParseException {

        Date date = simpleDateTimeFormat.parse("01-01-2020 00:00:00");

        LocalDateTime localDateTime = DateUtil.convertToLocalDateTime(date);

        assertEquals(2020, localDateTime.getYear());
        assertEquals(1, localDateTime.getMonthValue());
        assertEquals(1, localDateTime.getDayOfMonth());
        assertEquals(0, localDateTime.getHour());
        assertEquals(0, localDateTime.getMinute());
        assertEquals(0, localDateTime.getSecond());
    }

    @Test
    void shouldConvertToDate() throws ParseException {

        LocalDate localDate = LocalDate.of(2020, 1, 1);

        Date date = DateUtil.convertToDate(localDate);

        String dateString = simpleDateFormat.format(date);

        assertEquals("01-01-2020", dateString);
    }

    @Test
    void shouldNotConvertToDateWhenParameterIsNull() {

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            DateUtil.convertToDate(null);
        });

        assertEquals(businessException.getBaseErrorMessages(), GlobalErrorMessages.DATE_COULD_NOT_BE_CONVERTED);
    }

    @BeforeEach
    void setUp() {
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        simpleDateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    @AfterEach
    void tearDown() {
    }


}