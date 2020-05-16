package com.liam191.clockr.repo.db;

import org.threeten.bp.ZonedDateTime;

import androidx.room.TypeConverter;

class ZonedDateTimeConverter {
    @TypeConverter
    public static String fromZonedDateTime(ZonedDateTime zonedDateTime){
        return zonedDateTime.toString();
    }

    @TypeConverter
    public static ZonedDateTime fromString(String string){
        return ZonedDateTime.parse(string);
    }
}
