package com.liam191.clockr.repo;

import com.liam191.clockr.MainActivity;
import com.liam191.clockr.R;
import com.liam191.clockr.clocking.Clocking;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.Clock;
import org.threeten.bp.ZonedDateTime;

import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

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

    @Rule
    public ActivityTestRule activityTestRule;

    @Before
    public void refreshDatabase(){
         //ClockrApplicationTestRunner.FakeClockrApplication application =
         //       ((ClockrApplicationTestRunner.FakeClockrApplication) activityTestRule
         //               .getActivity()
         //               .getApplication());

        //TODO: NPE when calling getAppContainer() because once the activity is created it's too late to use another appContainer
        //      - See here for possible solutions: https://stackoverflow.com/a/23246170
        //TODO: Refactor this mess into separate classes
        //testAppContainer = application.getAppContainer();
        //testAppContainer.setAppClock(fakeClock);
        //Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "### testMainActivity - set clock");


        //testAppContainer.refreshData();
        //repository = testAppContainer.getClockingRepository();
    }

    @Test
    public void testMainActivity(){
        ZonedDateTime fakeNow = ZonedDateTime.parse("2020-03-04T10:00:00Z[Europe/London]");
        Clock fakeClock = Clock.fixed(fakeNow.toInstant(), fakeNow.getZone());

        activityTestRule = new ActivityTestRule(MainActivity.class);
        ClockrApplicationTestRunner.FakeClockrApplication.FakeAppContainerImpl appContainer = ((ClockrApplicationTestRunner.FakeClockrApplication) ApplicationProvider.getApplicationContext()).getAppContainer();
        appContainer.setAppClock(fakeClock);
        ClockingRepository repository = appContainer.getClockingRepository();

        Clocking clocking = new Clocking.Builder("hello world").startTime(ZonedDateTime.parse("2020-03-04T06:00Z[Europe/London]")).build();

        repository.insert(clocking);

        activityTestRule.launchActivity(null);

        //onView(withId(R.id.test_text))
        //        .check(matches(isDisplayed()))
        //        .check(matches(withText("["+ clocking.toString() +"]")));
    }

    @Test
    public void testMainActivity2(){
        ZonedDateTime fakeNow = ZonedDateTime.parse("2020-03-04T10:00:00Z[Europe/London]");
        Clock fakeClock = Clock.fixed(fakeNow.toInstant(), fakeNow.getZone());
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "### set activityRule");

        activityTestRule = new ActivityTestRule(MainActivity.class);
        ClockrApplicationTestRunner.FakeClockrApplication.FakeAppContainerImpl appContainer = ((ClockrApplicationTestRunner.FakeClockrApplication) ApplicationProvider.getApplicationContext()).getAppContainer();
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "### set appClock > "+ appContainer);

        appContainer.setAppClock(fakeClock);
        ClockingRepository repository = appContainer.getClockingRepository();

        Clocking clocking1 = new Clocking.Builder("hello world").startTime(ZonedDateTime.parse("2020-03-04T08:00Z[Europe/London]")).build();
        Clocking clocking2 = new Clocking.Builder("goodbye world").startTime(ZonedDateTime.parse("2020-03-04T09:00Z[Europe/London]")).build();

        repository.insert(clocking1);
        repository.insert(clocking2);

        activityTestRule.launchActivity(null);

        onView(withId(R.id.clocking_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(0))
                .check(matches(hasDescendant(withText(clocking1.label()))));
        onView(withId(R.id.clocking_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(1))
                .check(matches(hasDescendant(withText(clocking2.label()))));
    }
}
