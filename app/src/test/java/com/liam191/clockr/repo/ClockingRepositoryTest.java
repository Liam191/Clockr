package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InstantExecutorExtension.class)
public class ClockingRepositoryTest {

    // TODO: Refactor ClockingRepository to use a factory instead of singleton
    //          - Public factory and private constructor?
    //              - Other classes use factory (which calls constructor)
    //              - Tests use constructor
    // TODO: Try using in-memory RoomDatabase for testing.

    @Test
    public void testGetClockingsForGivenDay(){
        MutableLiveData<List<Clocking>> clockings = new ClockingRepository()
                .getAllForDate(new Date(2020, 3, 3));

        assertEquals(0, clockings.getValue().size());
    }

    @Test
    public void testAddClocking(){
        ClockingRepository repository = new ClockingRepository();
        Date testDay = new Date(2020, 3, 3);
        MutableLiveData<List<Clocking>> clockings = repository.getAllForDate(testDay);

        Clocking clocking = new Clocking.Builder("Test", 34)
                .startTime(testDay)
                .build();

        repository.add(clocking);
        assertTrue(repository.getAllForDate(testDay).getValue().contains(clocking));
    }
}