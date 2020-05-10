package com.liam191.clockr.repo;

import androidx.lifecycle.LiveData;

import com.liam191.clockr.clocking.Clocking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.threeten.bp.LocalDateTime;

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

    // ClockingDayView Factory
    @Test
    public void testFactoryOfDate_returnsSameInstanceWithSameDate(){
        ClockingDayView.Factory factory = new ClockingDayView.Factory();
        LocalDateTime testDate = LocalDateTime.of(2017, 4, 16, 0, 0 ,0);
        assertSame(factory.ofDate(testDate), factory.ofDate(testDate));
    }

    @Test
    public void testFactoryOfDate_returnsDifferentInstancesForDifferentDates(){
        ClockingDayView clockingDayView1 = new ClockingDayView.Factory().ofDate(LocalDateTime.of(2017, 4, 16,0,0,0));
        ClockingDayView clockingDayView2 = new ClockingDayView.Factory().ofDate(LocalDateTime.of(2017, 4, 17,0,0,0));

        assertNotSame(clockingDayView1, clockingDayView2);
    }




    @Test
    public void testGetAllForGivenDate_withNoData(){
        LiveData<List<Clocking>> clockings = new ClockingDayView.Factory()
                .ofDate(LocalDateTime.of(2020, 3, 3, 0, 0, 0))
                .get();

        assertEquals(0, clockings.getValue().size());
    }

    @Test
    public void testGetAllForGivenDate_ReturnsNewListOnUpdate(){
        LocalDateTime testDay = LocalDateTime.of(2020, 3, 3,0,0,0);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        LiveData<List<Clocking>> clockings = clockingDayView.get();

        List<Clocking> oldClockingList = clockings.getValue();
        oldClockingList.add(new Clocking.Builder("TestClocking1", testDay).build());
        clockingDayView.update(oldClockingList);

        oldClockingList.add(new Clocking.Builder("TestClocking2", testDay).build());
        List<Clocking> newClockingList = clockings.getValue();

        assertNotEquals(oldClockingList, newClockingList);
        assertNotSame(oldClockingList, newClockingList);
    }

    @Test
    public void testAddClocking(){
        LocalDateTime testDay = LocalDateTime.of(2020, 3, 3,0,0,0);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        LiveData<List<Clocking>> clockings = clockingDayView.get();

        List<Clocking> clockingList = clockings.getValue();
        clockingList.add(new Clocking.Builder("Test", testDay).build());
        clockingDayView.update(clockingList);

        assertEquals(clockingList, clockings.getValue());
    }

    @Test
    public void testRemoveClockings_withOneClocking(){
        LocalDateTime testDay = LocalDateTime.of(2020, 3, 3,0,0,0);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        LiveData<List<Clocking>> clockings = clockingDayView.get();

        Clocking clocking1 = new Clocking.Builder("TestClocking1", testDay)
                .build();

        List<Clocking> clockingList = clockings.getValue();

        clockingList.add(clocking1);
        clockingList.remove(clocking1);
        clockingDayView.update(clockingList);

        assertEquals(0, clockingDayView.get().getValue().size());
    }

    @Test
    public void testRemoveClockings_withThreeClockings(){
        LocalDateTime testDay = LocalDateTime.of(2020, 3, 3,0,0,0);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        List<Clocking> clockingList = clockingDayView.get().getValue();

        Clocking clocking1 = new Clocking.Builder("TestClocking1", testDay).build();
        Clocking clocking2 = new Clocking.Builder("TestClocking2", testDay).build();
        Clocking clocking3 = new Clocking.Builder("TestClocking3", testDay).build();

        clockingList.add(clocking1);
        clockingList.add(clocking2);
        clockingList.add(clocking3);
        clockingList.remove(clocking1);

        clockingDayView.update(clockingList);

        assertEquals(2, clockingDayView.get().getValue().size());
    }

    @Test
    public void testRemoveClockings_withNoClockings(){
        LocalDateTime testDay = LocalDateTime.of(2020, 3, 3,0,0,0);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        List<Clocking> clockingList = clockingDayView.get().getValue();

        Clocking clocking1 = new Clocking.Builder("TestClocking1", testDay).build();

        clockingList.remove(clocking1);
        clockingDayView.update(clockingList);

        assertEquals(0, clockingDayView.get().getValue().size());
    }

    @Test
    public void testRemoveClockings_withNull(){
        LocalDateTime testDay = LocalDateTime.of(2020, 3, 3,0,0,0);
        ClockingDayView clockingDayView = new ClockingDayView.Factory().ofDate(testDay);
        List<Clocking> clockingList = clockingDayView.get().getValue();

        clockingList.remove(null);
        clockingDayView.update(clockingList);

        assertEquals(0, clockingDayView.get().getValue().size());
    }
}