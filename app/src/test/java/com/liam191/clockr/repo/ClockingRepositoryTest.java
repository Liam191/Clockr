package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;
import androidx.lifecycle.LiveData;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InstantExecutorExtension.class)
public class ClockingRepositoryTest {

    // TODO: Refactor ClockingRepository to use a factory instead of singleton
    //          - Public factory and private constructor?
    //              - Other classes use factory (which calls constructor)
    //              - Tests use constructor

    @Test
    public void testClockingRepo_getClockingsForGivenDay(){
        LiveData<List<Clocking>> clockings = new ClockingRepository()
                .getAllForDate(new Date(2020, 3, 3));

        assertEquals(0, clockings.getValue().size());
    }

    @Test
    public void testClockingRepo_addClocking(){
        ClockingRepository repository = new ClockingRepository();
        Date testDay = new Date(2020, 3, 3);
        LiveData<List<Clocking>> clockings = repository.getAllForDate(testDay);

        Clocking clocking = new Clocking.Builder("Test", 34)
                .startTime(testDay)
                .build();

        repository.add(clocking);
        assertTrue(repository.getAllForDate(testDay).getValue().contains(clocking));
    }
}

class InstantExecutorExtension implements BeforeEachCallback, AfterEachCallback{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
            @Override public void executeOnDiskIO(@NonNull Runnable runnable) { runnable.run(); }
            @Override public void postToMainThread(@NonNull Runnable runnable) { runnable.run(); }
            @Override public boolean isMainThread() { return true; }
        });
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        ArchTaskExecutor.getInstance().setDelegate(null);
    }
}

