package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public final class ClockingDayView {

    private final MutableLiveData<List<Clocking>> clockings = new MutableLiveData<>();

    ClockingDayView(){
        clockings.postValue(new ArrayList<>());
    }

    public LiveData<List<Clocking>> getAllForDate(Date date){
        return clockings;
    }

    public void update(List<Clocking> newClockingList){
        clockings.postValue(new ArrayList<>(newClockingList));
    }
}
