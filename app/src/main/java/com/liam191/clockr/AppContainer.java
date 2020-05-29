package com.liam191.clockr;

import android.app.Application;

import androidx.room.Room;

import com.liam191.clockr.repo.ClockingDayViewModel;
import com.liam191.clockr.repo.ClockingRepository;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockrDatabase;

public class AppContainer {
    private ClockrDatabase clockrDatabase;
    private ClockingDao clockingDao;
    private ClockingRepository clockingRepository;
    private ClockingDayDao clockingDayDao;

    public AppContainer(Application application){
        clockrDatabase = Room.databaseBuilder(application.getApplicationContext(), ClockrDatabase.class, "clockrDatabase")
                .build();
        clockingDao = clockrDatabase.clockingDao();
        clockingDayDao = clockrDatabase.clockingDayDao();
        clockingRepository = new ClockingRepository(clockingDao);
    }

    public ClockingDayViewModel.Builder clockingDayViewModelBuilder(){
        return new ClockingDayViewModel.Builder(clockingRepository, clockingDayDao);
    }
}
