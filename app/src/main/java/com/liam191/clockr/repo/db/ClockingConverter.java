package com.liam191.clockr.repo.db;

import androidx.room.TypeConverter;

import com.liam191.clockr.clocking.Clocking;

import java.util.Date;

public class ClockingConverter {

    @TypeConverter
    public static ClockingEntity fromClocking(Clocking clocking){
        ClockingEntity entity = new ClockingEntity();
        entity.label = clocking.label();
        entity.description = clocking.description();
        entity.durationInMinutes = clocking.durationInMinutes();
        entity.startTime = clocking.startTime().getTime();
        return entity;
    }

    @TypeConverter
    public static Clocking fromClockingEntity(ClockingEntity entity){
        Date clockingStartTime = new Date();
        clockingStartTime.setTime(entity.startTime);

        return new Clocking.Builder(entity.label, entity.durationInMinutes, clockingStartTime)
                .description(entity.description)
                .build();
    }
}
