package com.liam191.clockr.repo.db;

import com.liam191.clockr.clocking.Clocking;

import java.util.Date;

public class ClockingMapper {

    private ClockingMapper(){}

    public static ClockingEntity map(Clocking clocking){
        ClockingEntity entity = new ClockingEntity();
        entity.label = clocking.label();
        entity.description = clocking.description();
        entity.durationInMinutes = clocking.durationInMinutes();
        entity.startTime = clocking.startTime().getTime();
        return entity;
    }

    public static Clocking map(ClockingEntity entity){
        Date clockingStartTime = new Date();
        clockingStartTime.setTime(entity.startTime);

        return new Clocking.Builder(entity.label, entity.durationInMinutes, clockingStartTime)
                .description(entity.description)
                .build();
    }
}
