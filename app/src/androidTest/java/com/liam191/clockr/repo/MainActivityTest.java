package com.liam191.clockr.repo;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.liam191.clockr.MainActivity;
import com.liam191.clockr.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class);
    //@Rule
    //public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testMainActivity(){
        onView(withId(R.id.test_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("[]")));
    }
}
