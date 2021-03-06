package com.liam191.clockr.repo;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.liam191.clockr.AppContainer;
import com.liam191.clockr.ClockrApplication;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockrDatabase;

import org.threeten.bp.Clock;

import androidx.room.Room;
import androidx.test.runner.AndroidJUnitRunner;

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
            AsyncTask.execute(() -> container = new FakeAppContainerImpl(getApplicationContext()));
        }

        public FakeAppContainerImpl getAppContainer(){
            return container;
        }

        //TODO: Refactor this mess into separate classes
        public static class FakeAppContainerImpl implements AppContainer {
            private Clock clock;
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
            public DayViewModel.Builder getDayViewModelBuilder() {
                return new DayViewModel.Builder(clockingRepository, clockingDayDao);
            }

            @Override
            public Clock getAppClock(){
                return clock;
            }

            public void setAppClock(Clock clock){
                this.clock = clock;
            }

            public ClockingRepository getClockingRepository(){
                return clockingRepository;
            }

            public ClockingDao getClockingDao(){
                return clockingDao;
            }
        }
    }
}
