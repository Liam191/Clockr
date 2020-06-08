package com.liam191.clockr.repo;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.liam191.clockr.MainActivity;
import com.liam191.clockr.R;
import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.db.ClockingDao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.Clock;
import org.threeten.bp.ZonedDateTime;

import java.util.logging.Level;
import java.util.logging.Logger;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class MainActivityTest {

    // TODO: Find a way to clear database between tests
    // TODO: Isolate dependency on time in Activities, maybe with set system time
    //       in tests or a getClock() method in AppContainer?

    public ClockingRepository repository;
    public ClockingDao clockingDao;
    private ZonedDateTime fakeNow = ZonedDateTime.parse("2020-03-04T10:00:00Z[Europe/London]");
    private Clock fakeClock = Clock.fixed(fakeNow.toInstant(), fakeNow.getZone());

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void refreshDatabase(){
        ClockrApplicationTestRunner.FakeClockrApplication application =
                ((ClockrApplicationTestRunner.FakeClockrApplication) activityTestRule
                        .getActivity()
                        .getApplication());

        //TODO: NPE when calling getAppContainer() because once the activity is created it's too late to use another appContainer
        //      - See here for possible solutions: https://stackoverflow.com/a/23246170
        application.setContainer(new ClockrApplicationTestRunner.FakeClockrApplication.FakeAppContainerImpl(application.getApplicationContext(), fakeClock));

        //TODO: Refactor this mess into separate classes
        ClockrApplicationTestRunner.FakeClockrApplication.FakeAppContainerImpl testAppContainer = application.getAppContainer();

        clockingDao = testAppContainer.getClockingDao();
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "### before > "+ clockingDao.getAll().toString());

        //testAppContainer.refreshData();
        repository = testAppContainer.getClockingRepository();
    }

    @Test
    public void testMainActivity(){
        Clocking clocking = new Clocking.Builder("hello world").startTime(ZonedDateTime.parse("2020-03-04T06:00Z[Europe/London]")).build();
        repository.insert(clocking);
        //onView(withId(R.id.test_text))
        //        .check(matches(isDisplayed()))
        //        .check(matches(withText("["+ clocking.toString() +"]")));
    }

    @Test
    public void testMainActivity2(){
        Clocking clocking1 = new Clocking.Builder("hello world").startTime(ZonedDateTime.parse("2020-03-04T08:00Z[Europe/London]")).build();
        Clocking clocking2 = new Clocking.Builder("goodbye world").startTime(ZonedDateTime.parse("2020-03-04T09:00Z[Europe/London]")).build();
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "### add clocking1");
        repository.insert(clocking1);
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "### before > "+ clockingDao.getAll().toString());

        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "### add clocking2");
        repository.insert(clocking2);

        onView(withId(R.id.clocking_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(0))
                .check(matches(hasDescendant(withText("hello world"))));
    }
}
