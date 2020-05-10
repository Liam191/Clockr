package com.liam191.clockr.repo.db;

import androidx.room.TypeConverter;

import org.threeten.bp.ZonedDateTime;

public class ZonedDateTimeConverter {
    @TypeConverter
    public static ZonedDateTime fromString(String time){
        return ZonedDateTime.parse(time);
    }

    @TypeConverter
    public static String fromZonedDateTime(ZonedDateTime dateTime){
        return dateTime.toString();
    }
}
