package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ClockingRepository {

    private ClockingRepository(){

    }

    private static class ClockingRepositorySingleton {
        private static final ClockingRepository INSTANCE = new ClockingRepository();
    }

    public static ClockingRepository getInstance(){
        return ClockingRepositorySingleton.INSTANCE;
    }

    public LiveData<List<Clocking>> getClockingsForDay(){
        MutableLiveData<List<Clocking>> clockings = new MutableLiveData<>();
        clockings.postValue(new ArrayList<>());
        return clockings;
    }
}
