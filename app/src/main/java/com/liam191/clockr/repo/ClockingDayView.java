package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockingEntity;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public final class ClockingDayView {

    // TODO: Use date to return new instances
    // TODO: Add DatabaseView annotation to DAO for ClockingDayView

    private final ClockingRepository clockingRepository;
    private final ZonedDateTime day;
    private final MutableLiveData<List<Clocking>> clockingList = new MutableLiveData<>();

    private final Observer<List<ClockingEntity>> liveDataObserver = clockingEntityList -> {
        List<Clocking> newClockingList = new ArrayList<>();
        for(ClockingEntity entity : clockingEntityList){
            newClockingList.add(ClockingRepository.Mapper.map(entity));
        }
        clockingList.postValue(newClockingList);
    };

    private ClockingDayView(ClockingRepository clockingRepository, ClockingDayDao clockingDayDao, ZonedDateTime day){
        this.clockingRepository = clockingRepository;
        this.day = day;

        // TODO: Replace with observe and lifecycle reference when implementing ViewModel
        clockingDayDao.getAllForDate(day.toString())
                .observeForever(liveDataObserver);
    }

    public LiveData<List<Clocking>> get(){
        return clockingList;
    }

    public void add(Clocking clocking){
        clockingRepository.insert(clocking);
    }

    public static class Factory {
        private final ClockingRepository clockingRepository;
        private final ClockingDayDao clockingDayDao;

        public Factory(ClockingRepository clockingRepository, ClockingDayDao clockingDayDao){
            this.clockingRepository = clockingRepository;
            this.clockingDayDao = clockingDayDao;
        }

        public ClockingDayView ofDate(ZonedDateTime date){
            return new ClockingDayView(clockingRepository, clockingDayDao, date);
        }
    }
}
