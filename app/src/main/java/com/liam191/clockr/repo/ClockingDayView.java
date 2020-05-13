package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public final class ClockingDayView {

    // TODO: Use date to return new instances
    // TODO: Add DatabaseView annotation to DAO for ClockingDayView

    private final MutableLiveData<List<Clocking>> clockings = new MutableLiveData<>();

    private ClockingDayView(ZonedDateTime date){
        clockings.postValue(new ArrayList<>());
    }

    public LiveData<List<Clocking>> get(){
        return clockings;
    }

    public static class Factory {
        private final ClockingDao clockingDao;

        public Factory(ClockingDao clockingDao){
            this.clockingDao = clockingDao;
        }

        public ClockingDayView ofDate(ZonedDateTime date){
            return new ClockingDayView(date);
        }
    }
}
