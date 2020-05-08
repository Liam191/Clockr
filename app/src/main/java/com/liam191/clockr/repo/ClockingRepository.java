package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingMapper;

final class ClockingRepository {

    private ClockingDao clockingDao;

    ClockingRepository(ClockingDao clockingDao){
        this.clockingDao = clockingDao;
    }

    void add(Clocking clocking){
        // TODO: map between dao and domain object
        clockingDao.add(ClockingMapper.map(clocking));
    }
}
