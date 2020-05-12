package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public final class ClockingDayView {

    //TODO: Add DatabaseView annotation to DAO for ClockingDayView

    private final MutableLiveData<List<Clocking>> clockings = new MutableLiveData<>();

    private ClockingDayView(ZonedDateTime date){
        clockings.postValue(new ArrayList<>());
    }

    private ClockingDayView(LocalDateTime date){
        this(ZonedDateTime.of(date, ZoneId.systemDefault()));
    }

    public LiveData<List<Clocking>> get(){
        return clockings;
    }

    public void update(List<Clocking> newClockingList){
        clockings.postValue(new ArrayList<>(newClockingList));
    }

    public static class Factory {
        private final Map<ZonedDateTime, ClockingDayView> cache;

        public Factory(){
            cache = new HashMap<>();
        }

        public ClockingDayView ofDate(ZonedDateTime date){
            if(cache.containsKey(date) == false){
                cache.put(date, new ClockingDayView(date));
            }
            return cache.get(date);
        }

        public ClockingDayView ofDate(LocalDateTime date){
            return ofDate(ZonedDateTime.of(date, ZoneId.systemDefault()));
        }
    }
}
