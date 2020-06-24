package com.liam191.clockr.repo;


import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockingEntity;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public final class DayViewModel extends ViewModel {

    private final ClockingRepository clockingRepository;
    private final ZonedDateTime day;
    private final MutableLiveData<List<Clocking>> clockingList = new MutableLiveData<>();

    private DayViewModel(ClockingRepository clockingRepository, ClockingDayDao clockingDayDao, ZonedDateTime day){
        this.clockingRepository = clockingRepository;
        this.day = day;

        List<Clocking> newClockings = new ArrayList<>();
        for(ClockingEntity entity : clockingDayDao.getAllForDate(day)){
            newClockings.add(ClockingRepository.Mapper.map(entity));
        }
        clockingList.postValue(newClockings);

    }

    public LiveData<List<Clocking>> get(){
        return clockingList;
    }

    public void add(Clocking clocking){
        clockingRepository.insert(clocking);
    }

    public void remove(Clocking clocking){
        clockingRepository.delete(clocking);
    }

    public void replace(Clocking target, Clocking replacement){
        clockingRepository.replace(target, replacement);
    }

    public static class Builder implements ViewModelProvider.Factory {
        private final ClockingRepository clockingRepository;
        private final ClockingDayDao clockingDayDao;
        private ZonedDateTime ofDate;

        public Builder(ClockingRepository clockingRepository, ClockingDayDao clockingDayDao){
            if(clockingRepository == null){
                throw new IllegalArgumentException("clockingRepository cannot be null");
            }
            if(clockingDayDao == null){
                throw new IllegalArgumentException("clockingDayDao cannot be null");
            }

            this.clockingRepository = clockingRepository;
            this.clockingDayDao = clockingDayDao;
        }

        public Builder ofDate(ZonedDateTime ofDate){
            if(ofDate == null){
                throw new IllegalArgumentException("ofDate cannot be null");
            }
            this.ofDate = ofDate;
            return this;
        }

        DayViewModel build(){
            if(ofDate == null){
                throw new IllegalArgumentException("ofDate must be set");
            }
            return new DayViewModel(clockingRepository, clockingDayDao, ofDate);
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if(modelClass != DayViewModel.class){
                throw new IllegalArgumentException("modelClass must be of type ClockingDayViewModel");
            }
            return (T) build();
        }
    }
}
