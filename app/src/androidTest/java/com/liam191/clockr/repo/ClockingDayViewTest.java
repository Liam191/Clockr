package com.liam191.clockr.repo;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import androidx.test.runner.AndroidJUnit4;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockrDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.ZonedDateTime;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class ClockingDayViewTest {

    // TODO: Test ClockingDayView with different dates
    // TODO: Cache for ClockingDayView instances of different dates?
    //          - Static factory with cache can't be easily tested.
    //          - Use service locator for instantiation and caching instead?

    private ClockrDatabase testDb;
    private ClockingDao testClockingDao;
    private ClockingDayDao testClockingDayDao;
    private ClockingRepository testRepository;
    private ClockingDayView.Factory clockingViewFactory;

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Before
    public void createTestDb(){
        Context appContext = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(appContext, ClockrDatabase.class).build();

        testClockingDao = testDb.clockingDao();
        testClockingDayDao = testDb.clockingDayDao();
        testRepository = new ClockingRepository(testClockingDao);

        clockingViewFactory = new ClockingDayView.Factory(testRepository, testClockingDayDao);
    }

    @After
    public void teardownTestDb(){
        testDb.close();
    }



    // ClockingDayView Factory
    @Test
    public void testFactoryOfDate_returnsNewInstanceWithSameDate(){
        ZonedDateTime testDate = ZonedDateTime.parse("2017-04-16T12:00:00Z[Europe/London]");
        assertNotSame(clockingViewFactory.ofDate(testDate), clockingViewFactory.ofDate(testDate));
    }

    @Test
    public void testFactoryOfDate_returnsNewInstancesForDifferentDates(){
        ClockingDayView clockingDayView1 = clockingViewFactory.ofDate(ZonedDateTime.parse("2017-04-16T12:00:00Z[Europe/London]"));
        ClockingDayView clockingDayView2 = clockingViewFactory.ofDate(ZonedDateTime.parse("2017-04-17T12:00:00Z[Europe/London]"));

        assertNotSame(clockingDayView1, clockingDayView2);
    }



    //ClockingDayView
    @Test
    public void testGet_WithNoData(){
        ClockingDayView view = clockingViewFactory.ofDate(ZonedDateTime.parse("2020-03-03T12:00:00Z[Europe/London]"));
        assertEquals(0, view.get().getValue().size());
    }

    @Test
    public void testGet_WithMultipleDatesSelectsOnlyGivenDate(){
        ZonedDateTime expectedDate = ZonedDateTime.parse("2020-09-01T13:02:03+01:00[Europe/London]");

        ZonedDateTime otherDate1 = expectedDate.minusDays(2);
        ZonedDateTime otherDate2 = expectedDate.minusDays(1);
        ZonedDateTime otherDate3 = expectedDate.plusDays(1);

        Clocking expectedClocking1 = new Clocking.Builder("Expected1").startTime(expectedDate.minusMinutes(200)).build();
        Clocking expectedClocking2 = new Clocking.Builder("Expected2").startTime(expectedDate).build();
        Clocking expectedClocking3 = new Clocking.Builder("Expected3").startTime(expectedDate.plusMinutes(250)).build();

        // Populate test database
        testRepository.insert(new Clocking.Builder("Test1").startTime(otherDate1).build());
        testRepository.insert(new Clocking.Builder("Test2").startTime(otherDate1).build());
        testRepository.insert(new Clocking.Builder("Test3").startTime(otherDate2).build());
        testRepository.insert(new Clocking.Builder("Test4").startTime(otherDate2).build());
        testRepository.insert(expectedClocking1);
        testRepository.insert(expectedClocking2);
        testRepository.insert(expectedClocking3);
        testRepository.insert(new Clocking.Builder("Test8").startTime(otherDate3).build());
        testRepository.insert(new Clocking.Builder("Test9").startTime(otherDate3).build());

        ClockingDayView view = clockingViewFactory
                .ofDate(expectedDate);

        assertEquals(3, getLiveDataUpdates(view.get()).size());
        assertTrue(getLiveDataUpdates(view.get()).contains(expectedClocking1));
        assertTrue(getLiveDataUpdates(view.get()).contains(expectedClocking2));
        assertTrue(getLiveDataUpdates(view.get()).contains(expectedClocking3));
    }

    @Test
    public void testGet_AddUpdatesLiveData(){
        ZonedDateTime testDate = ZonedDateTime.parse("2020-10-11T17:02:03+01:00[Europe/London]");
        ClockingDayView view = clockingViewFactory
                .ofDate(testDate);

        view.add(new Clocking.Builder("Test").startTime(testDate).build());
        assertEquals(1, getLiveDataUpdates(view.get()).size());
    }



    // LiveData helper method
    static List getLiveDataUpdates(LiveData liveData){
        final List[] container = new List[1];
        liveData.observeForever(new Observer<List<Clocking>>() {
            @Override
            public void onChanged(List<Clocking> clockings) {
                container[0] = clockings;
                liveData.removeObserver(this);
            }
        });
        return container[0];
    }
}