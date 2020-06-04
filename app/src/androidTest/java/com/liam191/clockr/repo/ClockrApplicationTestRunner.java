package com.liam191.clockr.repo;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.test.runner.AndroidJUnitRunner;

import com.liam191.clockr.AppContainer;
import com.liam191.clockr.ClockrApplication;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockrDatabase;

public class ClockrApplicationTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, FakeClockrApplication.class.getName(), context);
    }

    public static class FakeClockrApplication extends ClockrApplication {
        private FakeAppContainerImpl container;

        @Override
        public void onCreate(){
            super.onCreate();
            container = new FakeAppContainerImpl(getApplicationContext());
        }

        public FakeAppContainerImpl getAppContainer(){
            return container;
        }

        public class FakeAppContainerImpl implements AppContainer {
            private final ClockrDatabase clockrDatabase;
            private final ClockingDao clockingDao;
            private final ClockingRepository clockingRepository;
            private final ClockingDayDao clockingDayDao;

            public FakeAppContainerImpl(Context applicationContext){
                clockrDatabase = Room.inMemoryDatabaseBuilder(applicationContext, ClockrDatabase.class)
                        .build();
                clockingDao = clockrDatabase.clockingDao();
                clockingDayDao = clockrDatabase.clockingDayDao();
                clockingRepository = new ClockingRepository(clockingDao);
            }

            @Override
            public ClockingDayViewModel.Builder clockingDayViewModelBuilder() {
                return new ClockingDayViewModel.Builder(clockingRepository, clockingDayDao);
            }

            public ClockingRepository getClockingRepository(){
                return clockingRepository;
            }

            public void refreshData(){
                clockrDatabase.clearAllTables();
            }
        }
    }
}
