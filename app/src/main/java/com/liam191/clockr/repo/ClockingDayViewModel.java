package com.liam191.clockr.repo;


import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockingEntity;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

public final class ClockingDayViewModel extends ViewModel {

    private final ClockingRepository clockingRepository;
    private final ZonedDateTime day;
    private final LiveData<List<Clocking>> clockingList;

    private final Function<List<ClockingEntity>, LiveData<List<Clocking>>>
            clockingSwitchMapFunction = clockingEntities -> {

        List<Clocking> newClockings = new ArrayList<>();
        for(ClockingEntity entity : clockingEntities){
            newClockings.add(ClockingRepository.Mapper.map(entity));
        }

        MutableLiveData<List<Clocking>> newLiveData = new MutableLiveData<>();
        newLiveData.postValue(newClockings);

        return newLiveData;
    };

    private ClockingDayViewModel(ClockingRepository clockingRepository, ClockingDayDao clockingDayDao, ZonedDateTime day){
        this.clockingRepository = clockingRepository;
        this.day = day;

        clockingList = Transformations.switchMap(
                clockingDayDao.getAllForDate(day),
                clockingSwitchMapFunction
        );
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

        ClockingDayViewModel build(){
            if(ofDate == null){
                throw new IllegalArgumentException("ofDate must be set");
            }
            return new ClockingDayViewModel(clockingRepository, clockingDayDao, ofDate);
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) build();
        }
    }
}
