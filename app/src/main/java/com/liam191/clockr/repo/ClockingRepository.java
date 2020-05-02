package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public final class ClockingRepository {

    private final MutableLiveData<List<Clocking>> clockings = new MutableLiveData<>();

    ClockingRepository(){
        clockings.postValue(new ArrayList<>());
    }

    public MutableLiveData<List<Clocking>> getAllForDate(Date date){
        return clockings;
    }

    public void add(Clocking clocking){
        ArrayList<Clocking> newClockings = new ArrayList<>(clockings.getValue());
        newClockings.add(clocking);
        clockings.postValue(newClockings);
    }

    public void remove(Clocking clocking){
        List newClockings = clockings.getValue();
        newClockings.remove(clocking);
        clockings.postValue(newClockings);
    }
}
