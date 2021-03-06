package com.liam191.clockr.repo;

import android.content.Context;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockrDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SmallTest
public class DayViewModelTest {

    // TODO: Use ClockingDayView add/replace/delete methods instead of CLockingRepository
    // TODO: Test edge cases with Factory.ofDate()
    //          - Midnight in another timezone that would go back to the previous day with offset

    private ClockrDatabase testDb;
    private ClockingDayDao testClockingDayDao;
    private ClockingRepository testRepository;
    private DayViewModel.Builder clockingViewBuilder;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Before
    public void createTestDb(){
        Context appContext = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(appContext, ClockrDatabase.class).build();

        ClockingDao testClockingDao = testDb.clockingDao();
        testClockingDayDao = testDb.clockingDayDao();

        testRepository = new ClockingRepository(testClockingDao);
        clockingViewBuilder = new DayViewModel.Builder(testRepository, testClockingDayDao);
    }


    // ClockingDayView Factory
    @Test
    public void testFactoryBuild_withNullRepositoryThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("clockingRepository cannot be null");
        ZonedDateTime testDate = ZonedDateTime.parse("2017-04-16T12:00:00Z[Europe/London]");

        new DayViewModel.Builder(null, testClockingDayDao)
                .ofDate(testDate)
                .build();
    }

    @Test
    public void testFactoryBuild_withNullClockingDayDaoThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("clockingDayDao cannot be null");
        ZonedDateTime testDate = ZonedDateTime.parse("2017-04-16T12:00:00Z[Europe/London]");

        new DayViewModel.Builder(testRepository, null)
                .ofDate(testDate)
                .build();
    }

    @Test
    public void testFactoryBuild_withNullOfDateThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("ofDate cannot be null");

        new DayViewModel.Builder(testRepository, testClockingDayDao)
                .ofDate(null)
                .build();
    }

    @Test
    public void testFactoryBuild_withoutOfDateThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("ofDate must be set");

        new DayViewModel.Builder(testRepository, testClockingDayDao)
                .build();
    }

    @Test
    public void testFactoryCreate_withWrongClassThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("modelClass must be of type ClockingDayViewModel");

        new DayViewModel.Builder(testRepository, testClockingDayDao)
                .ofDate(ZonedDateTime.parse("2017-04-16T12:00:00Z[Europe/London]"))
                .create(ViewModel.class);
    }

    @Test
    public void testFactoryCreate_withCorrectClass(){
        DayViewModel viewModel = new DayViewModel.Builder(testRepository, testClockingDayDao)
                .ofDate(ZonedDateTime.parse("2017-04-16T12:00:00Z[Europe/London]"))
                .create(DayViewModel.class);
        assertTrue(viewModel != null);
        assertEquals(viewModel.getClass(), DayViewModel.class);
    }

    @Test
    public void testFactoryOfDate_returnsNewInstanceWithSameDate(){
        ZonedDateTime testDate = ZonedDateTime.parse("2017-04-16T12:00:00Z[Europe/London]");
        assertNotSame(clockingViewBuilder.ofDate(testDate).build(), clockingViewBuilder.ofDate(testDate).build());
    }

    @Test
    public void testFactoryOfDate_returnsNewInstancesForDifferentDates(){
        DayViewModel view1 = clockingViewBuilder.ofDate(ZonedDateTime.parse("2017-04-16T12:00:00Z[Europe/London]")).build();
        DayViewModel view2 = clockingViewBuilder.ofDate(ZonedDateTime.parse("2017-04-17T12:00:00Z[Europe/London]")).build();
        assertNotSame(view1, view2);
    }



    //ClockingDayView get()
    @Test
    public void testGet_WithNoData(){
        DayViewModel view = clockingViewBuilder.ofDate(ZonedDateTime.parse("2020-03-03T12:00:00Z[Europe/London]")).build();
        assertEquals(0, DayViewModelTest.getOrAwaitValue(view.get()).size());
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

        // Populate test database using Repository before creating ClockingDayView instance
        testRepository.insert(new Clocking.Builder("Test1").startTime(otherDate1).build());
        testRepository.insert(new Clocking.Builder("Test2").startTime(otherDate1).build());
        testRepository.insert(new Clocking.Builder("Test3").startTime(otherDate2).build());
        testRepository.insert(new Clocking.Builder("Test4").startTime(otherDate2).build());
        testRepository.insert(expectedClocking1);
        testRepository.insert(expectedClocking2);
        testRepository.insert(expectedClocking3);
        testRepository.insert(new Clocking.Builder("Test8").startTime(otherDate3).build());
        testRepository.insert(new Clocking.Builder("Test9").startTime(otherDate3).build());

        DayViewModel view = clockingViewBuilder.ofDate(expectedDate).build();

        assertEquals(3, DayViewModelTest.getOrAwaitValue(view.get()).size());
        assertTrue(DayViewModelTest.getOrAwaitValue(view.get()).contains(expectedClocking1));
        assertTrue(DayViewModelTest.getOrAwaitValue(view.get()).contains(expectedClocking2));
        assertTrue(DayViewModelTest.getOrAwaitValue(view.get()).contains(expectedClocking3));
    }

    @Test
    public void testGet_AddUpdatesLiveData() {
        ZonedDateTime testDate = ZonedDateTime.parse("2020-10-11T17:02:03+01:00[Europe/London]");
        DayViewModel view = clockingViewBuilder.ofDate(testDate).build();

        view.add(new Clocking.Builder("Test").startTime(testDate).build());
        assertEquals(1, DayViewModelTest.getOrAwaitValue(view.get()).size());
    }



    // ClockingDayView add()
    @Test
    public void testAdd(){
        ZonedDateTime testDate = ZonedDateTime.parse("2020-08-19T12:00:00Z[Europe/London]");
        DayViewModel view = clockingViewBuilder.ofDate(testDate).build();

        view.add(new Clocking.Builder("Test").startTime(testDate).build());
        assertEquals(1, DayViewModelTest.getOrAwaitValue(view.get()).size());
    }



    // ClockingDayView delete()
    @Test
    public void testDelete() {
        ZonedDateTime testDate = ZonedDateTime.parse("2020-08-19T12:00:00Z[Europe/London]");
        DayViewModel view = clockingViewBuilder.ofDate(testDate).build();
        Clocking testClocking = new Clocking.Builder("Test").startTime(testDate).build();

        view.add(testClocking);
        assertEquals(1, getOrAwaitValue(view.get()).size());

        view.remove(testClocking);
        assertEquals(0, getOrAwaitValue(view.get()).size());

    }

    @Test
    public void testDelete_WithMultipleClockings(){
        ZonedDateTime testDate = ZonedDateTime.parse("2020-08-19T12:00:00Z[Europe/London]");
        DayViewModel view = clockingViewBuilder.ofDate(testDate).build();

        Clocking testClocking1 = new Clocking.Builder("Test 1").startTime(testDate).build();
        Clocking testClocking2 = new Clocking.Builder("Test 2").startTime(testDate.plusMinutes(20)).build();
        Clocking testClocking3 = new Clocking.Builder("Test 3").startTime(testDate.plusMinutes(40)).build();

        view.add(testClocking1);
        view.add(testClocking2);
        view.add(testClocking3);
        assertEquals(3, DayViewModelTest.getOrAwaitValue(view.get()).size());

        view.remove(testClocking1);
        assertEquals(2, DayViewModelTest.getOrAwaitValue(view.get()).size());
    }



    // ClockingDayView replace()
    @Test
    public void testReplace(){
        ZonedDateTime testDate = ZonedDateTime.parse("2020-08-19T12:00:00Z[Europe/London]");
        DayViewModel view = clockingViewBuilder.ofDate(testDate).build();
        Clocking targetClocking = new Clocking.Builder("Test 1").startTime(testDate).build();
        Clocking replacementClocking = new Clocking.Builder("Test 2").startTime(testDate.plusMinutes(20)).build();

        view.add(targetClocking);

        assertEquals(1, DayViewModelTest.getOrAwaitValue(view.get()).size());
        assertEquals(targetClocking, DayViewModelTest.getOrAwaitValue(view.get()).get(0));

        view.replace(targetClocking, replacementClocking);

        assertEquals(1, DayViewModelTest.getOrAwaitValue(view.get()).size());
        assertEquals(replacementClocking, DayViewModelTest.getOrAwaitValue(view.get()).get(0));
    }

    @Test
    public void testReplace_WithMultipleClockings(){
        ZonedDateTime testDate = ZonedDateTime.parse("2020-08-19T12:00:00Z[Europe/London]");
        DayViewModel view = clockingViewBuilder.ofDate(testDate).build();
        Clocking testClocking1 = new Clocking.Builder("Test 1").startTime(testDate).build();
        Clocking testClocking2 = new Clocking.Builder("Test 2").startTime(testDate.plusMinutes(20)).build();
        Clocking testClocking3 = new Clocking.Builder("Test 3").startTime(testDate.plusMinutes(40)).build();
        Clocking replacementClocking = new Clocking.Builder("Test 4").startTime(testDate.plusMinutes(100)).build();

        view.add(testClocking1);
        view.add(testClocking2);
        view.add(testClocking3);

        assertEquals(3, getOrAwaitValue(view.get()).size());

        List<Clocking> expectedList = new ArrayList<>(Arrays.asList(
                testClocking1,
                testClocking2,
                replacementClocking
        ));

        view.replace(testClocking3, replacementClocking);
        assertEquals(3, getOrAwaitValue(view.get()).size());
        assertEquals(expectedList, getOrAwaitValue(view.get()));
    }


    public static <T> T getOrAwaitValue(final LiveData<T> liveData){
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(2);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();

                if(latch.getCount() == 0) {
                    liveData.removeObserver(this);
                }
            }
        };
        liveData.observeForever(observer);

        try{
            latch.await(200, TimeUnit.MILLISECONDS);
        } catch(Exception e){
            Logger.getAnonymousLogger().log(Level.INFO, "## DayViewModelTEST - getOrAwaitValue timed out!");
        }
        //noinspection unchecked
        return (T) data[0];
    }
}