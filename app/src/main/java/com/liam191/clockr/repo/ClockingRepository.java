package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingEntity;

import org.threeten.bp.ZonedDateTime;

final class ClockingRepository {

    private ClockingDao clockingDao;

    ClockingRepository(ClockingDao clockingDao){
        this.clockingDao = clockingDao;
    }

    void insert(Clocking clocking){
        clockingDao.insert(Mapper.map(clocking));
    }

    void delete(Clocking clocking){
        ClockingEntity entity = Mapper.map(clocking);
        clockingDao.delete(entity.label, entity.description, entity.startTime, entity.endTime);
    }


    static final class Mapper {

        private Mapper(){}

        static ClockingEntity map(Clocking clocking){
            ClockingEntity entity = new ClockingEntity();
            entity.label = clocking.label();
            entity.description = clocking.description();
            entity.startTime = clocking.startTime().toString();
            entity.endTime = clocking.endTime().toString();
            return entity;
        }

        static Clocking map(ClockingEntity entity){
            return new Clocking.Builder(entity.label)
                    .startTime(ZonedDateTime.parse(entity.startTime))
                    .endTime(ZonedDateTime.parse(entity.endTime))
                    .description(entity.description)
                    .build();
        }
    }
}
