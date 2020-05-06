package com.liam191.clockr.repo;

import com.liam191.clockr.clocking.Clocking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InstantExecutorExtension.class)
public class ClockingDayViewTest {

    // TODO: Refactor ClockingRepository to use a factory instead of singleton
    //          - Public factory and private constructor?
    //              - Other classes use factory (which calls constructor)
    //              - Tests use constructor
    // TODO: Try using in-memory RoomDatabase for testing.
    // TODO: Test getAllForDate() with different dates

    @Test
    public void testGetAllForGivenDate_withNoData(){
        LiveData<List<Clocking>> clockings = new ClockingDayView()
                .getAllForDate(new Date(2020, 3, 3));

        assertEquals(0, clockings.getValue().size());
    }

    @Test
    public void testGetAllForGivenDate_ReturnsNewListOnUpdate(){
        ClockingDayView repository = new ClockingDayView();
        Date testDay = new Date(2020, 3, 3);
        LiveData<List<Clocking>> clockings = repository.getAllForDate(testDay);

        List<Clocking> oldClockingList = clockings.getValue();
        oldClockingList.add(new Clocking.Builder("TestClocking1", 34, testDay).build());
        repository.update(oldClockingList);

        oldClockingList.add(new Clocking.Builder("TestClocking2", 46, testDay).build());
        List<Clocking> newClockingList = clockings.getValue();

        assertNotEquals(oldClockingList, newClockingList);
        assertNotSame(oldClockingList, newClockingList);
    }

    @Test
    public void testAddClocking(){
        ClockingDayView repository = new ClockingDayView();
        Date testDay = new Date(2020, 3, 3);
        LiveData<List<Clocking>> clockings = new ClockingDayView().getAllForDate(testDay);

        List<Clocking> clockingList = clockings.getValue();
        clockingList.add(new Clocking.Builder("Test", 34, testDay).build());
        repository.update(clockingList);

        assertEquals(clockingList, clockings.getValue());
    }

    @Test
    public void testRemoveClockings_withOneClocking(){
        ClockingDayView repository = new ClockingDayView();
        Date testDay = new Date(2020, 3, 3);
        LiveData<List<Clocking>> clockings = new ClockingDayView().getAllForDate(testDay);

        Clocking clocking1 = new Clocking.Builder("TestClocking1", 34, testDay)
                .build();

        List<Clocking> clockingList = clockings.getValue();

        clockingList.add(clocking1);
        clockingList.remove(clocking1);
        repository.update(clockingList);

        assertEquals(0, repository.getAllForDate(testDay).getValue().size());
    }

    @Test
    public void testRemoveClockings_withThreeClockings(){
        ClockingDayView repository = new ClockingDayView();
        Date testDay = new Date(2020, 3, 3);
        List<Clocking> clockingList = repository.getAllForDate(testDay).getValue();

        Clocking clocking1 = new Clocking.Builder("TestClocking1", 34, testDay).build();
        Clocking clocking2 = new Clocking.Builder("TestClocking2", 47, testDay).build();
        Clocking clocking3 = new Clocking.Builder("TestClocking3", 23, testDay).build();

        clockingList.add(clocking1);
        clockingList.add(clocking2);
        clockingList.add(clocking3);
        clockingList.remove(clocking1);

        repository.update(clockingList);

        assertEquals(2, repository.getAllForDate(testDay).getValue().size());
    }

    @Test
    public void testRemoveClockings_withNoClockings(){
        ClockingDayView repository = new ClockingDayView();
        Date testDay = new Date(2020, 3, 3);
        List<Clocking> clockingList = repository.getAllForDate(testDay).getValue();

        Clocking clocking1 = new Clocking.Builder("TestClocking1", 34, testDay).build();

        clockingList.remove(clocking1);
        repository.update(clockingList);

        assertEquals(0, repository.getAllForDate(testDay).getValue().size());
    }

    @Test
    public void testRemoveClockings_withNull(){
        ClockingDayView repository = new ClockingDayView();
        Date testDay = new Date(2020, 3, 3);

        List<Clocking> clockingList = repository.getAllForDate(testDay).getValue();
        clockingList.remove(null);
        repository.update(clockingList);

        assertEquals(0, repository.getAllForDate(testDay).getValue().size());
    }
}