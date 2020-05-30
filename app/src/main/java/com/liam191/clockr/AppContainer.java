package com.liam191.clockr;

import android.content.Context;

import androidx.room.Room;

import com.liam191.clockr.repo.ClockingDayViewModel;
import com.liam191.clockr.repo.ClockingRepository;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockrDatabase;

public final class AppContainer {
    private final ClockrDatabase clockrDatabase;
    private final ClockingDao clockingDao;
    private final ClockingRepository clockingRepository;
    private final ClockingDayDao clockingDayDao;

    public AppContainer(Context applicationContext){
        clockrDatabase = Room.databaseBuilder(applicationContext, ClockrDatabase.class, "clockrDatabase")
                .build();
        clockingDao = clockrDatabase.clockingDao();
        clockingDayDao = clockrDatabase.clockingDayDao();
        clockingRepository = new ClockingRepository(clockingDao);
    }

    public ClockingDayViewModel.Builder clockingDayViewModelBuilder(){
        return new ClockingDayViewModel.Builder(clockingRepository, clockingDayDao);
    }
}
