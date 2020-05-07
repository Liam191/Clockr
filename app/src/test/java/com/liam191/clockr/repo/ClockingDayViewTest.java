package com.liam191.clockr.repo;

import androidx.lifecycle.LiveData;

import com.liam191.clockr.clocking.Clocking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(InstantExecutorExtension.class)
public class ClockingDayViewTest {

    // TODO: Try using in-memory RoomDatabase for testing.
    // TODO: Test ClockingDayView with different dates
    // TODO: Cache for ClockingDayView instances of different dates?
    //          - Static factory with cache can't be easily tested.
    //          - Use service locator for instantiation and caching instead?

    @Test
    public void testFactoryOfDate_returnsSameInstance(){
        ClockingDayView.Factory factory = new ClockingDayView.Factory();
        Date testDate = new Date(2017 - 1900, 4, 16);
        assertSame(factory.ofDate(testDate), factory.ofDate(testDate));
    }

    @Test
    public void testGetAllForGivenDate_withNoData(){
        LiveData<List<Clocking>> clockings = new ClockingDayView.Factory()
                .ofDate(new Date(2020 - 1900, 3, 3))
                .get();

        assertEquals(0, clockings.getValue().size());
    }

    @Test
    public void testGetAllForGivenDate_ReturnsNewListOnUpdate(){
        Date testDay = new Date(2020 - 1900, 3, 3);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        LiveData<List<Clocking>> clockings = clockingDayView.get();

        List<Clocking> oldClockingList = clockings.getValue();
        oldClockingList.add(new Clocking.Builder("TestClocking1", 34, testDay).build());
        clockingDayView.update(oldClockingList);

        oldClockingList.add(new Clocking.Builder("TestClocking2", 46, testDay).build());
        List<Clocking> newClockingList = clockings.getValue();

        assertNotEquals(oldClockingList, newClockingList);
        assertNotSame(oldClockingList, newClockingList);
    }

    @Test
    public void testAddClocking(){
        Date testDay = new Date(2020 - 1900, 3, 3);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        LiveData<List<Clocking>> clockings = clockingDayView.get();

        List<Clocking> clockingList = clockings.getValue();
        clockingList.add(new Clocking.Builder("Test", 34, testDay).build());
        clockingDayView.update(clockingList);

        assertEquals(clockingList, clockings.getValue());
    }

    @Test
    public void testRemoveClockings_withOneClocking(){
        Date testDay = new Date(2020 - 1900, 3, 3);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        LiveData<List<Clocking>> clockings = clockingDayView.get();

        Clocking clocking1 = new Clocking.Builder("TestClocking1", 34, testDay)
                .build();

        List<Clocking> clockingList = clockings.getValue();

        clockingList.add(clocking1);
        clockingList.remove(clocking1);
        clockingDayView.update(clockingList);

        assertEquals(0, clockingDayView.get().getValue().size());
    }

    @Test
    public void testRemoveClockings_withThreeClockings(){
        Date testDay = new Date(2020 - 1900, 3, 3);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        List<Clocking> clockingList = clockingDayView.get().getValue();

        Clocking clocking1 = new Clocking.Builder("TestClocking1", 34, testDay).build();
        Clocking clocking2 = new Clocking.Builder("TestClocking2", 47, testDay).build();
        Clocking clocking3 = new Clocking.Builder("TestClocking3", 23, testDay).build();

        clockingList.add(clocking1);
        clockingList.add(clocking2);
        clockingList.add(clocking3);
        clockingList.remove(clocking1);

        clockingDayView.update(clockingList);

        assertEquals(2, clockingDayView.get().getValue().size());
    }

    @Test
    public void testRemoveClockings_withNoClockings(){
        Date testDay = new Date(2020 - 1900, 3, 3);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        List<Clocking> clockingList = clockingDayView.get().getValue();

        Clocking clocking1 = new Clocking.Builder("TestClocking1", 34, testDay).build();

        clockingList.remove(clocking1);
        clockingDayView.update(clockingList);

        assertEquals(0, clockingDayView.get().getValue().size());
    }

    @Test
    public void testRemoveClockings_withNull(){
        Date testDay = new Date(2020 - 1900, 3, 3);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        List<Clocking> clockingList = clockingDayView.get().getValue();

        clockingList.remove(null);
        clockingDayView.update(clockingList);

        assertEquals(0, clockingDayView.get().getValue().size());
    }
}