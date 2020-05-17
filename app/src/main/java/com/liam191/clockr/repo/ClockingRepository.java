package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingEntity;

final class ClockingRepository {

    private ClockingDao clockingDao;

    ClockingRepository(ClockingDao clockingDao){
        this.clockingDao = clockingDao;
    }

    void insert(Clocking clocking){
        if(clocking == null){
            throw new IllegalArgumentException("Cannot insert null Clocking");
        }

        clockingDao.insert(Mapper.map(clocking));
    }

    void delete(Clocking clocking){
        if(clocking == null){
            throw new IllegalArgumentException("Cannot delete null Clocking");
        }

        ClockingEntity entity = Mapper.map(clocking);
        clockingDao.delete(entity);
    }

    void replace(Clocking target, Clocking replacement){
        if(target == null || replacement == null){
            throw new IllegalArgumentException("target or replacement cannot be null");
        }

        clockingDao.replace(Mapper.map(target), Mapper.map(replacement));
    }


    static final class Mapper {

        private Mapper(){}

        static ClockingEntity map(Clocking clocking){
            ClockingEntity entity = new ClockingEntity();
            entity.label = clocking.label();
            entity.description = clocking.description();
            entity.startTime = clocking.startTime();
            entity.endTime = clocking.endTime();
            return entity;
        }

        static Clocking map(ClockingEntity entity){
            return new Clocking.Builder(entity.label)
                    .startTime(entity.startTime)
                    .endTime(entity.endTime)
                    .description(entity.description)
                    .build();
        }
    }
}
