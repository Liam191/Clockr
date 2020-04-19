package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ClockingRepository {

    private final
    private final MutableLiveData<List<Clocking>> clockings = new MutableLiveData<>();
    {
        clockings.postValue(new ArrayList<>());
    }

    ClockingRepository(){

    }

    private static class ClockingRepositorySingleton {
        private static final ClockingRepository INSTANCE = new ClockingRepository();
    }

    public static ClockingRepository getInstance(){
        return ClockingRepositorySingleton.INSTANCE;
    }

    public LiveData<List<Clocking>> getClockingsForDate(Date date){
        return clockings;
    }

    public void addClocking(Clocking clocking){
        ArrayList<Clocking> newClockings = new ArrayList<>(clockings.getValue());
        newClockings.add(clocking);
        clockings.postValue(newClockings);
    }
}
