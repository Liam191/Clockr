package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import java.util.ArrayList;
import java.util.List;

final class ClockingRepository {

    private List<Clocking> clockingList = new ArrayList<>();

    ClockingRepository(){

    }

    void add(Clocking clocking){
        // TODO: map between dao and domain object
        clockingList.add(clocking);
    }
}
