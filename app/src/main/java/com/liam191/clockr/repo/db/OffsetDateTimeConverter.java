package com.liam191.clockr.repo.db;

import androidx.room.TypeConverter;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class OffsetDateTimeConverter {
    @TypeConverter
    public static OffsetDateTime fromUtcOffsetString(String offsetTime){
        return OffsetDateTime.parse(offsetTime);
    }

    @TypeConverter
    public static String fromOffsetDateTime(OffsetDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
