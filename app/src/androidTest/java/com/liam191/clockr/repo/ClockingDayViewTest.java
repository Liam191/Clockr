package com.liam191.clockr.repo;

import android.content.Context;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockrDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class ClockingDayViewTest {

    // TODO: Test ClockingDayView with different dates
    // TODO: Cache for ClockingDayView instances of different dates?
    //          - Static factory with cache can't be easily tested.
    //          - Use service locator for instantiation and caching instead?

    private ClockrDatabase testDb;
    private ClockingDao clockingTestDao;

    @Before
    public void createTestDb(){
        Context appContext = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(appContext, ClockrDatabase.class).build();
        clockingTestDao = testDb.clockingDao();
    }

    @After
    public void teardownTestDb(){
        testDb.close();
    }


    // ClockingDayView Factory
    @Test
    public void testFactoryOfDate_returnsNewInstanceWithSameDate(){
        ClockingDayView.Factory factory = new ClockingDayView.Factory(clockingTestDao);
        ZonedDateTime testDate = ZonedDateTime.of(LocalDateTime.of(2017, 4, 16, 0, 0 ,0), ZoneId.systemDefault());
        assertNotSame(factory.ofDate(testDate), factory.ofDate(testDate));
    }

    @Test
    public void testFactoryOfDate_returnsDifferentInstancesForDifferentDates(){
        ClockingDayView clockingDayView1 = new ClockingDayView.Factory(clockingTestDao).ofDate(ZonedDateTime.of(LocalDateTime.of(2017, 4, 16,0,0,0), ZoneId.systemDefault()));
        ClockingDayView clockingDayView2 = new ClockingDayView.Factory(clockingTestDao).ofDate(ZonedDateTime.of(LocalDateTime.of(2017, 4, 17,0,0,0), ZoneId.systemDefault()));

        assertNotSame(clockingDayView1, clockingDayView2);
    }




    @Test
    public void testGetAllForGivenDate_withNoData(){
        LiveData<List<Clocking>> clockings = new ClockingDayView.Factory(clockingTestDao)
                .ofDate(ZonedDateTime.of(LocalDateTime.of(2020, 3, 3, 0, 0, 0), ZoneId.systemDefault()))
                .get();

        assertEquals(0, clockings.getValue().size());
    }
/*
    @Test
    public void testGetAllForGivenDate_ReturnsNewListOnUpdate(){
        ZonedDateTime testDay = ZonedDateTime.of(2020, 3, 3,0,0,0,0, ZoneId.systemDefault());
        ClockingDayView clockingDayView = new ClockingDayView.Factory(clockingTestDao).ofDate(testDay);
        LiveData<List<Clocking>> clockings = clockingDayView.get();

        List<Clocking> oldClockingList = clockings.getValue();
        oldClockingList.add(new Clocking.Builder("TestClocking1").startTime(testDay).build());
        clockingDayView.update(oldClockingList);

        oldClockingList.add(new Clocking.Builder("TestClocking2").startTime(testDay).build());
        List<Clocking> newClockingList = clockings.getValue();

        assertNotEquals(oldClockingList, newClockingList);
        assertNotSame(oldClockingList, newClockingList);
    }

    @Test
    public void testAddClocking(){
        ZonedDateTime testDay = ZonedDateTime.of(2020, 3, 3,0,0,0,0, ZoneId.systemDefault());
        ClockingDayView clockingDayView = new ClockingDayView.Factory(clockingTestDao).ofDate(testDay);
        LiveData<List<Clocking>> clockings = clockingDayView.get();

        List<Clocking> clockingList = clockings.getValue();
        clockingList.add(new Clocking.Builder("Test").startTime(testDay).build());
        clockingDayView.update(clockingList);

        assertEquals(clockingList, clockings.getValue());
    }

    @Test
    public void testRemoveClockings_withOneClocking(){
        ZonedDateTime testDay = ZonedDateTime.of(2020, 3, 3,0,0,0,0, ZoneId.systemDefault());
        ClockingDayView clockingDayView = new ClockingDayView.Factory(clockingTestDao).ofDate(testDay);
        LiveData<List<Clocking>> clockings = clockingDayView.get();

        Clocking clocking1 = new Clocking.Builder("TestClocking1").startTime(testDay)
                .build();

        List<Clocking> clockingList = clockings.getValue();

        clockingList.add(clocking1);
        clockingList.remove(clocking1);
        clockingDayView.update(clockingList);

        assertEquals(0, clockingDayView.get().getValue().size());
    }

    @Test
    public void testRemoveClockings_withThreeClockings(){
        ZonedDateTime testDay = ZonedDateTime.of(2020, 3, 3,0,0,0,0, ZoneId.systemDefault());
        ClockingDayView clockingDayView = new ClockingDayView.Factory(clockingTestDao).ofDate(testDay);
        List<Clocking> clockingList = clockingDayView.get().getValue();

        Clocking clocking1 = new Clocking.Builder("TestClocking1").startTime(testDay).build();
        Clocking clocking2 = new Clocking.Builder("TestClocking2").startTime(testDay).build();
        Clocking clocking3 = new Clocking.Builder("TestClocking3").startTime(testDay).build();

        clockingList.add(clocking1);
        clockingList.add(clocking2);
        clockingList.add(clocking3);
        clockingList.remove(clocking1);

        clockingDayView.update(clockingList);

        assertEquals(2, clockingDayView.get().getValue().size());
    }

    @Test
    public void testRemoveClockings_withNoClockings(){
        ZonedDateTime testDay = ZonedDateTime.of(2020, 3, 3,0,0,0,0, ZoneId.systemDefault());
        ClockingDayView clockingDayView = new ClockingDayView.Factory(clockingTestDao).ofDate(testDay);
        List<Clocking> clockingList = clockingDayView.get().getValue();

        Clocking clocking1 = new Clocking.Builder("TestClocking1").startTime(testDay).build();

        clockingList.remove(clocking1);
        clockingDayView.update(clockingList);

        assertEquals(0, clockingDayView.get().getValue().size());
    }

    @Test
    public void testRemoveClockings_withNull(){
        ZonedDateTime testDay = ZonedDateTime.of(2020, 3, 3,0,0,0,0, ZoneId.systemDefault());
        ClockingDayView clockingDayView = new ClockingDayView.Factory(clockingTestDao).ofDate(testDay);
        List<Clocking> clockingList = clockingDayView.get().getValue();

        clockingList.remove(null);
        clockingDayView.update(clockingList);

        assertEquals(0, clockingDayView.get().getValue().size());
    }
    */
}