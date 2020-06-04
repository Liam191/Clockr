package com.liam191.clockr.repo;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.liam191.clockr.MainActivity;
import com.liam191.clockr.R;
import com.liam191.clockr.clocking.Clocking;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.ZonedDateTime;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class MainActivityTest {

    // TODO: Find a way to clear database between tests
    // TODO: Isolate dependency on time in Activities, maybe with set system time
    //       in tests or a getClock() method in AppContainer?

    public ClockingRepository repository;

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void refreshDatabase(){
        ClockrApplicationTestRunner.FakeClockrApplication application =
                ((ClockrApplicationTestRunner.FakeClockrApplication) activityTestRule
                        .getActivity()
                        .getApplication());
        //TODO: Refactor this mess into separate classes
        ClockrApplicationTestRunner.FakeClockrApplication.FakeAppContainerImpl testAppContainer = application.getAppContainer();
        testAppContainer.refreshData();
        repository = testAppContainer.getClockingRepository();
    }

    @Test
    public void testMainActivity(){
        Clocking clocking = new Clocking.Builder("hello world").startTime(ZonedDateTime.parse("2020-06-04T06:00Z[Europe/London]")).build();
        repository.insert(clocking);
        onView(withId(R.id.test_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("["+ clocking.toString() +"]")));
    }

    @Test
    public void testMainActivity2(){
        Clocking clocking1 = new Clocking.Builder("hello world").startTime(ZonedDateTime.parse("2020-06-04T08:00Z[Europe/London]")).build();
        Clocking clocking2 = new Clocking.Builder("goodbye world").startTime(ZonedDateTime.parse("2020-06-04T09:00Z[Europe/London]")).build();
        repository.insert(clocking1);
        repository.insert(clocking2);
        onView(withId(R.id.test_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("["+ clocking1.toString() +", "+ clocking2.toString() +"]")));
    }
}
