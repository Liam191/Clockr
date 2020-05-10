package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingEntity;

final class ClockingRepository {

    private ClockingDao clockingDao;

    ClockingRepository(ClockingDao clockingDao){
        this.clockingDao = clockingDao;
    }

    void add(Clocking clocking){
        // TODO: map between dao and domain object
        clockingDao.add(Mapper.map(clocking));
    }


    public static class Mapper {

        private Mapper(){}

        public static ClockingEntity map(Clocking clocking){
            ClockingEntity entity = new ClockingEntity();
            entity.label = clocking.label();
            entity.description = clocking.description();
            entity.durationInMinutes = clocking.durationInMinutes();
            entity.startTime = clocking.startTime();
            entity.endTime = clocking.endTime();
            return entity;
        }

        public static Clocking map(ClockingEntity entity){
            return new Clocking.Builder(entity.label, entity.startTime, entity.endTime)
                    .description(entity.description)
                    .build();
        }
    }
}
