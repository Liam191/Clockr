package com.liam191.clockr.repo.db;

import org.junit.jupiter.api.Test;
import org.threeten.bp.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZonedDateTimeConverterTest {
    // TODO: Handling errors if conversion fails
    // TODO: Complete testing here
    @Test
    public void testConverter_fromZonedDateTimeToString(){
        ZonedDateTime testDate = ZonedDateTime.parse("2020-01-03T00:00+00:00[Europe/London]");
        String convertedTime = ZonedDateTimeConverter.fromZonedDateTime(testDate);
        assertEquals(testDate.toString(), convertedTime);
    }

    @Test
    public void testConverter_fromEntityToClocking(){
        String testString = "2020-01-03T00:00+00:00[Europe/London]";
        ZonedDateTime testDate = ZonedDateTime.parse(testString);
        ZonedDateTime convertedTime = ZonedDateTimeConverter.fromString(testString);
        assertEquals(testDate, convertedTime);
    }
}
