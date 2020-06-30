package com.liam191.clockr.repo;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.LargeTest;

import com.liam191.clockr.R;
import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.gui.dayview.ClockrFragmentFactory;
import com.liam191.clockr.gui.dayview.DayListFragment;
import com.liam191.clockr.repo.db.ClockingDao;
import com.liam191.clockr.repo.db.ClockingDayDao;
import com.liam191.clockr.repo.db.ClockrDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.Clock;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@LargeTest
public class DayListFragmentTest {

    private ClockrDatabase testDb;
    private ClockingDayDao testClockingDayDao;
    private ClockingRepository testRepository;
    private DayViewModel.Builder clockingViewBuilder;

    // TODO: Implement custom ViewMatchers for Clockings (remove all the excess matcher code in tests)

    @Before
    public void createTestDb(){
        Context appContext = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(appContext, ClockrDatabase.class).build();

        ClockingDao testClockingDao = testDb.clockingDao();
        testClockingDayDao = testDb.clockingDayDao();

        testRepository = new ClockingRepository(testClockingDao);
        clockingViewBuilder = new DayViewModel.Builder(testRepository, testClockingDayDao);
    }

    @After
    public void teardownTestDb(){
        testDb.close();
    }


    @Test
    public void testMainActivity(){
        ZonedDateTime fakeNow = ZonedDateTime.parse("2020-03-04T10:00:00Z[Europe/London]");
        Clock fakeClock = Clock.fixed(fakeNow.toInstant(), fakeNow.getZone());
        ClockrApplicationTestRunner.FakeClockrApplication.FakeAppContainerImpl appContainer = ((ClockrApplicationTestRunner.FakeClockrApplication) ApplicationProvider.getApplicationContext()).getAppContainer();
        appContainer.setAppClock(fakeClock);

        ZonedDateTime testDate = ZonedDateTime.parse("2020-03-04T08:11Z[Europe/London]");


        Clocking clocking1 = new Clocking.Builder("hello world")
                .startTime(testDate.plusMinutes(1))
                .endTime(testDate.plusMinutes(10))
                .description("This is a description for the first clocking.").build();
        Clocking clocking2 = new Clocking.Builder("goodbye world")
                .startTime(testDate.plusMinutes(2))
                .endTime(testDate.plusMinutes(50))
                .description("This a description for the second clocking.").build();
        Clocking clocking3 = new Clocking.Builder("new world")
                .startTime(testDate.plusMinutes(3))
                .endTime(testDate.plusMinutes(120)).build();
        Clocking clocking4 = new Clocking.Builder("old world")
                .startTime(testDate.plusDays(5).plusMinutes(4))
                .description("This clocking has a different start date and shouldn't show up").build();

        testRepository.insert(clocking1);
        testRepository.insert(clocking2);
        testRepository.insert(clocking3);
        testRepository.insert(clocking4);

        FragmentScenario.launchInContainer(DayListFragment.class, null, new ClockrFragmentFactory(clockingViewBuilder));

        onView(withId(R.id.clocking_recyclerview))
                .check(matches(hasChildCount(3)))
                // clocking1
                .check(matches(hasDescendant(withText(clocking1.label()))))
                .check(matches(hasDescendant(withText(clocking1.description()))))
                .check(matches(hasDescendant(withText("9m"))))
                .check(matches(hasDescendant(withText(clocking1.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))))))
                // clocking2
                .check(matches(hasDescendant(withText(clocking2.label()))))
                .check(matches(hasDescendant(withText(clocking2.description()))))
                .check(matches(hasDescendant(withText("48m"))))
                .check(matches(hasDescendant(withText(clocking2.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))))))
                // clocking3
                .check(matches(hasDescendant(withText(clocking3.label()))))
                .check(matches(not(hasDescendant(withText(clocking3.description())))))
                .check(matches(hasDescendant(withText("117m"))))
                .check(matches(hasDescendant(withText(clocking3.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))))))
                // clocking4 - should not be displayed because it's from another date
                .check(matches(not(hasDescendant(withText(clocking4.label())))))
                .check(matches(not(hasDescendant(withText(clocking4.description())))))
                .check(matches(not(hasDescendant(withText("26m")))))
                .check(matches(not(hasDescendant(withText(clocking4.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)))))));

    }

    @Test
    public void testMainActivity_WithDifferentDates(){
        ZonedDateTime fakeNow = ZonedDateTime.parse("2020-03-04T10:00:00Z[Europe/London]");
        Clock fakeClock = Clock.fixed(fakeNow.toInstant(), fakeNow.getZone());
        ClockrApplicationTestRunner.FakeClockrApplication.FakeAppContainerImpl appContainer = ((ClockrApplicationTestRunner.FakeClockrApplication) ApplicationProvider.getApplicationContext()).getAppContainer();
        appContainer.setAppClock(fakeClock);

        ZonedDateTime testDate = ZonedDateTime.parse("2020-03-04T08:11Z[Europe/London]");

        Clocking clocking1 = new Clocking.Builder("hello world")
                .startTime(testDate.plusMinutes(1))
                .endTime(testDate.plusMinutes(10))
                .description("This is a description for the first clocking.").build();
        Clocking clocking2 = new Clocking.Builder("old world")
                .startTime(testDate.plusDays(1).plusMinutes(4))
                .description("This clocking has a different start date and shouldn't show up").build();

        testRepository.insert(clocking1);
        testRepository.insert(clocking2);

        // TODO: Fix 'View null does not have a NavController set' error in tests
        //       - Look into NavController testing video
        FragmentScenario.launchInContainer(DayListFragment.class, null, new ClockrFragmentFactory(clockingViewBuilder));

        onView(withId(R.id.clocking_recyclerview))
                .check(matches(hasChildCount(1)))
                // clocking1
                .check(matches(hasDescendant(withText(clocking1.label()))))
                .check(matches(hasDescendant(withText(clocking1.description()))))
                .check(matches(hasDescendant(withText("9m"))))
                .check(matches(hasDescendant(withText(clocking1.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))))))
                // clocking2 - should not be displayed because it's from another date
                .check(matches(not(hasDescendant(withText(clocking2.label())))))
                .check(matches(not(hasDescendant(withText(clocking2.description())))))
                .check(matches(not(hasDescendant(withText("26m")))))
                .check(matches(not(hasDescendant(withText(clocking2.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)))))));


        onView(withId(R.id.toolbar_next)).perform(click());

        onView(withId(R.id.clocking_recyclerview))
                .check(matches(hasChildCount(1)))
                // clocking1 - should not be displayed after changing the date
                .check(matches(not(hasDescendant(withText(clocking1.label())))))
                .check(matches(not(hasDescendant(withText(clocking1.description())))))
                .check(matches(not(hasDescendant(withText("9m")))))
                .check(matches(not(hasDescendant(withText(clocking1.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)))))))
                // clocking2
                .check(matches(hasDescendant(withText(clocking2.label()))))
                .check(matches(hasDescendant(withText(clocking2.description()))))
                .check(matches(hasDescendant(withText("26m"))))
                .check(matches(hasDescendant(withText(clocking2.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))))));
    }
}
