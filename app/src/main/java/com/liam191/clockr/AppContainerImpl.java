package com.liam191.clockr;

import android.content.Context;

import com.liam191.clockr.repo.DayViewModel;
import com.liam191.clockr.repo.ClockingRepository;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockrDatabase;

import org.threeten.bp.Clock;

import androidx.room.Room;

public class AppContainerImpl implements AppContainer{
    private final ClockrDatabase clockrDatabase;
    private final ClockingDao clockingDao;
    private final ClockingRepository clockingRepository;
    private final ClockingDayDao clockingDayDao;

    public AppContainerImpl(Context applicationContext){
        clockrDatabase = Room.databaseBuilder(applicationContext, ClockrDatabase.class, "clockrDatabase")
                .build();
        clockingDao = clockrDatabase.clockingDao();
        clockingDayDao = clockrDatabase.clockingDayDao();
        clockingRepository = new ClockingRepository(clockingDao);
    }

    @Override
    public DayViewModel.Builder clockingDayViewModelBuilder(){
        return new DayViewModel.Builder(clockingRepository, clockingDayDao);
    }

    @Override
    public Clock getAppClock(){
        return Clock.systemDefaultZone();
    }
}
