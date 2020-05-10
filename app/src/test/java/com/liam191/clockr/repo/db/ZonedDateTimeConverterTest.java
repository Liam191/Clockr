package com.liam191.clockr.repo.db;

import org.junit.jupiter.api.Test;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZonedDateTimeConverterTest {
    // TODO: Handling errors if conversion fails
    // TODO: Complete testing here
    @Test
    public void testConverter_fromZonedDateTimeToString(){
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        String convertedTime = ZonedDateTimeConverter.fromZonedDateTime(zdt);
        assertEquals(zdt.toString(), convertedTime);
    }

    @Test
    public void testConverter_fromEntityToClocking(){
        ClockingEntity entity = new ClockingEntity();

    }
}
