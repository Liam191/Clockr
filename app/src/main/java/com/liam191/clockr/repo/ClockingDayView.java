package com.liam191.clockr.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.liam191.clockr.clocking.Clocking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ClockingDayView {

    private final MutableLiveData<List<Clocking>> clockings = new MutableLiveData<>();

    private ClockingDayView(Date date){
        clockings.postValue(new ArrayList<>());
    }

    public LiveData<List<Clocking>> get(){
        return clockings;
    }

    public void update(List<Clocking> newClockingList){
        clockings.postValue(new ArrayList<>(newClockingList));
    }

    public static class Factory {
        private final Map<Date, ClockingDayView> cache;

        public Factory(){
            cache = new HashMap<>();
        }

        public ClockingDayView ofDate(Date date){
            if(cache.containsKey(date) == false){
                cache.put(date, new ClockingDayView(date));
            }
            return cache.get(date);
        }
    }
}
